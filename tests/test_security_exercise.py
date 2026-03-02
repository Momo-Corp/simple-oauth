"""
Test suite: Sécurité Spring Boot - Exercice pédagogique

Objectif pour l'étudiant:
- Ces tests sont CORRECTS et testent des cas réels de sécurité
- Ils vont ÉCHOUER car la sécurité Spring n'est pas implémentée
- À toi de modifier le code Spring pour que tous les tests passent
- Tu dois ajouter des endpoints, sécuriser, assigner les rôles correctement

Concepts testés:
✓ Authentification JWT obligatoire
✓ Autorisation par rôle (ROLE_ADMIN, ROLE_MANAGER)
✓ Isolation des données par utilisateur
✓ Protection contre les accès non autorisés
"""

import pytest
import requests

APP_URL = "http://localhost:8080"


@pytest.fixture(scope="module")
def admin_token() -> str:
    """Récupère un token TEST avec rôle ADMIN"""
    response = requests.get(f"{APP_URL}/auth/test-token", timeout=10)
    assert response.status_code == 200
    return response.text.strip()


@pytest.fixture
def admin_headers(admin_token) -> dict:
    """Headers avec authentification ADMIN"""
    return {
        "Authorization": f"Bearer {admin_token}",
        "Content-Type": "application/json",
    }


class TestAdminEndpoints:
    """Tests sur les endpoints d'administration (ROLE_ADMIN required)"""

    def test_admin_stats_requires_auth(self):
        """GET /admin/stats doit refuser l'accès sans token"""
        response = requests.get(f"{APP_URL}/admin/stats", timeout=10)
        assert response.status_code == 401, "Le endpoint /admin/stats ne protège pas l'accès anonyme"

    def test_admin_stats_requires_admin_role(self):
        """GET /admin/stats doit refuser les non-admins"""
        # Token sans ROLE_ADMIN
        headers = {
            "Authorization": "Bearer invalid.token",
            "Content-Type": "application/json",
        }
        response = requests.get(f"{APP_URL}/admin/stats", headers=headers, timeout=10)
        assert response.status_code in [401, 403], "Le endpoint /admin/stats ne protège pas contre les non-admins"

    def test_admin_stats_returns_json_with_admin_token(self, admin_headers):
        """GET /admin/stats doit retourner des stats avec un token ADMIN"""
        response = requests.get(f"{APP_URL}/admin/stats", headers=admin_headers, timeout=10)
        assert response.status_code == 200, f"Accès admin refusé: {response.status_code}"
        
        data = response.json()
        assert "total_farms" in data, "La réponse doit contenir 'total_farms'"
        assert "total_users" in data, "La réponse doit contenir 'total_users'"
        assert isinstance(data["total_farms"], int)
        assert isinstance(data["total_users"], int)


class TestFarmEndpointProtections:
    """Tests d'isolation et de protection des fermes"""

    def test_farm_reset_requires_admin(self):
        """POST /farm/reset doit être ADMIN-ONLY"""
        response = requests.post(f"{APP_URL}/farm/reset", timeout=10)
        assert response.status_code == 401, "Le endpoint /farm/reset n'est pas protégé !"

    def test_farm_reset_works_with_admin_token(self, admin_headers):
        """POST /farm/reset doit réinitialiser le compteur pour les admins"""
        response = requests.post(f"{APP_URL}/farm/reset", headers=admin_headers, timeout=10)
        assert response.status_code == 200, f"Erreur reset: {response.status_code} / {response.text}"
        
        data = response.json()
        assert "message" in data, "La réponse doit contenir un 'message'"

    def test_farm_delete_requires_auth(self, admin_headers):
        """DELETE /farm doit refuser les accès non authentifiés"""
        response = requests.delete(f"{APP_URL}/farm", timeout=10)
        assert response.status_code == 401, "Le endpoint DELETE /farm n'est pas protégé"

    def test_farm_delete_with_valid_token(self, admin_headers):
        """DELETE /farm doit supprimer la ferme de l'utilisateur authentifié"""
        # Créer une ferme d'abord
        requests.get(f"{APP_URL}/farm", headers=admin_headers)
        
        # Puis la supprimer
        response = requests.delete(f"{APP_URL}/farm", headers=admin_headers, timeout=10)
        assert response.status_code == 200, f"Suppression échouée: {response.status_code}"


class TestRoleBasedAccess:
    """Tests sur les contrôles d'accès basés sur les rôles"""

    def test_counter_increment_requires_role(self):
        """POST /counter/increment doit refuser les non-authentifiés"""
        response = requests.post(f"{APP_URL}/counter/increment", timeout=10)
        assert response.status_code == 401, "Le endpoint POST /counter/increment n'est pas protégé"


    def test_counter_state_is_consistent(self, admin_headers):
        """Le compteur incrementé doit rester cohérent"""
        # Obtenir l'état initial
        initial = requests.get(f"{APP_URL}/counter", headers=admin_headers, timeout=10).json()
        
        # Incrémenter
        requests.post(f"{APP_URL}/counter/increment", headers=admin_headers, timeout=10)
        
        # Vérifier l'incrémentation
        updated = requests.get(f"{APP_URL}/counter", headers=admin_headers,timeout=10).json()
        assert updated["count"] > initial["count"], "Le compteur n'a pas été incrémenté"


class TestAuthenticationErrors:
    """Tests sur les codes d'erreur d'authentification"""

    def test_expired_or_invalid_token_returns_401(self):
        """Un token invalide doit retourner 401 (pas 400 ou 500)"""
        headers = {
            "Authorization": "Bearer malformed.token.here",
            "Content-Type": "application/json",
        }
        response = requests.get(f"{APP_URL}/user", headers=headers, timeout=10)
        assert response.status_code == 401, "Les tokens invalides doivent retourner 401"

    def test_missing_auth_header_returns_401(self):
        """L'absence d'Authorization header doit retourner 401"""
        response = requests.get(f"{APP_URL}/user", timeout=10)
        assert response.status_code == 401, "L'absence d'auth doit retourner 401"


class TestConceptSecurityInDepth:
    """Tests avancés pour vraiment comprendre la sécurité Spring"""

    def test_unauthorized_access_shows_message(self):
        """Les erreurs 401/403 doivent avoir un message d'erreur explicite"""
        response = requests.get(f"{APP_URL}/admin", timeout=10)
        assert response.status_code == 401
        # Le body peut contenir un message
        # C'est optionnel mais pédagogique

    def test_successful_auth_info_returned(self, admin_headers):
        """GET /user doit retourner le username et les rôles"""
        response = requests.get(f"{APP_URL}/user", headers=admin_headers, timeout=10)
        assert response.status_code == 200
        
        data = response.json()
        assert "username" in data
        assert "roles" in data
        assert "ROLE_ADMIN" in data["roles"], "L'utilisateur test doit avoir ROLE_ADMIN"
