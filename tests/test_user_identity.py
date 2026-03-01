import pytest
import requests

APP_URL = "http://localhost:8080"


@pytest.fixture(scope="module")
def test_jwt_token() -> str:
    """Récupère un token JWT de test depuis l'API."""
    response = requests.get(f"{APP_URL}/auth/test-token", timeout=10)
    assert response.status_code == 200, f"Impossible d'obtenir un token: {response.text}"
    token = response.text.strip()
    assert token, "Le token retourné est vide"
    return token


def test_user_requires_authentication():
    """/user doit être protégé et refuser les requêtes anonymes."""
    response = requests.get(f"{APP_URL}/user", timeout=10)
    assert response.status_code == 401


def test_user_rejects_invalid_token():
    """/user doit refuser un JWT invalide."""
    headers = {
        "Authorization": "Bearer invalid.jwt.token",
        "Accept": "application/json",
    }
    response = requests.get(f"{APP_URL}/user", headers=headers, timeout=10)
    assert response.status_code in [401, 403]


def test_user_identity_matches_test_token_subject_and_role(test_jwt_token):
    """Le payload /user doit refléter l'identité contenue dans le token de test."""
    headers = {
        "Authorization": f"Bearer {test_jwt_token}",
        "Accept": "application/json",
    }
    response = requests.get(f"{APP_URL}/user", headers=headers, timeout=10)

    assert response.status_code == 200, f"Accès refusé: {response.status_code} / {response.text}"

    payload = response.json()
    assert set(payload.keys()) == {"username", "roles"}
    assert payload["username"] == "test-admin"
    assert isinstance(payload["roles"], list)
    assert "ROLE_ADMIN" in payload["roles"]


def test_user_roles_contains_only_strings(test_jwt_token):
    """La liste des rôles doit contenir uniquement des chaînes."""
    headers = {
        "Authorization": f"Bearer {test_jwt_token}",
        "Accept": "application/json",
    }
    response = requests.get(f"{APP_URL}/user", headers=headers, timeout=10)

    assert response.status_code == 200, f"Accès refusé: {response.status_code} / {response.text}"

    payload = response.json()
    assert all(isinstance(role, str) for role in payload["roles"])
