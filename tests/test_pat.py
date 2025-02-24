import os
import requests

BASE_URL = "https://api.github.com"

# Utilise `GITHUB_TOKEN` en CI/CD, sinon utilise `GITHUB_PAT` pour les tests en local
GITHUB_TOKEN = os.getenv("MY_GITHUB_PAT")

def test_github_oauth():
    """Teste l'authentification GitHub OAuth avec le bon token selon l'environnement"""

    headers = {
        "Authorization": f"Bearer {GITHUB_TOKEN}",
        "Accept": "application/vnd.github.v3+json"
    }

    response = requests.get(f"{BASE_URL}/user", headers=headers)

    assert response.status_code == 200
    assert "login" in response.json()  # Vérifie que l'utilisateur est authentifié


def test_protected_route_without_token():
    """Test should fail: Trying to access /user without a valid token"""

    response = requests.get(f"{BASE_URL}/user")  # No token sent

    assert response.status_code == 401, f"Expected 401, got {response.status_code}"