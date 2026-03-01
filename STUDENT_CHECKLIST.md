# ✅ Checklist - Exercice Sécurité Spring Boot

## Phase 1: Compréhension

- [ ] J'ai lu `SECURITY_EXERCISE.md`
- [ ] J'ai lancé l'app Spring avec `mvn spring-boot:run`
- [ ] J'ai lancé les tests avec `pytest tests/test_security_exercise.py -v`
- [ ] J'ai identifié les tests qui failent
- [ ] Je comprends que les endpoints manquent (404) ou ne sont pas sécurisés

## Phase 2: Implémentation d'AdminController

**Endpoint à implémenter:** `GET /admin/stats`

- [ ] J'ai ouvert `src/main/java/com/example/authgithub/controller/AdminController.java`
- [ ] J'ai complété la méthode `adminStats()`
- [ ] La méthode a `@GetMapping("/stats")`
- [ ] La méthode a `@PreAuthorize("hasRole('ADMIN')")`
- [ ] Elle retourne un `Map.of("total_farms", ..., "total_users", ...)`
- [ ] TEST: `test_admin_stats_returns_json_with_admin_token` passe ✅

## Phase 3: Implémentation de FarmController

**Endpoints à implémenter:** `DELETE /farm` et `POST /reset`

### DELETE /farm
- [ ] J'ai ouvert `src/main/java/com/example/authgithub/controller/FarmController.java`
- [ ] La méthode a `@DeleteMapping`
- [ ] La méthode a `@PreAuthorize("isAuthenticated()")`
- [ ] Elle accepte `Authentication auth` en paramètre
- [ ] Elle retourne un `Map.of("message", "Farm deleted successfully")`
- [ ] TEST: `test_farm_delete_requires_auth` passe ✅
- [ ] TEST: `test_farm_delete_with_valid_token` passe ✅

### POST /reset
- [ ] La méthode a `@PostMapping("/reset")`
- [ ] La méthode a `@PreAuthorize("hasRole('ADMIN')")`
- [ ] Elle retourne un `Map.of("message", "All farms have been reset")`
- [ ] TEST: `test_farm_reset_requires_admin` passe ✅
- [ ] TEST: `test_farm_reset_works_with_admin_token` passe ✅

## Phase 4: Vérification de CounterController

**Endpoints déjà implémentés: `GET /counter` et `POST /counter/increment`**

- [ ] `GET /counter` est **public** (pas d'annotation `@PreAuthorize`)
- [ ] `GET /counter` retourne `{"count": N}`
- [ ] `POST /counter/increment` a `@PreAuthorize("isAuthenticated()")`
- [ ] `POST /counter/increment` incrémente le compteur
- [ ] TEST: `test_counter_view_is_public` passe ✅
- [ ] TEST: `test_counter_increment_requires_role` passe ✅
- [ ] TEST: `test_counter_state_is_consistent` passe ✅

## Phase 5: Tests d'authentification/autorisation

- [ ] TEST: `test_admin_stats_requires_auth` passe ✅
- [ ] TEST: `test_admin_stats_requires_admin_role` passe ✅
- [ ] TEST: `test_unauthorized_access_shows_message` passe ✅
- [ ] TEST: `test_expired_or_invalid_token_returns_401` passe ✅
- [ ] TEST: `test_missing_auth_header_returns_401` passe ✅

## Phase 6: Tous les tests passent?

Lancer:
```bash
pytest tests/test_security_exercise.py -v
```

**Résultat attendu:**
```
======================== 13 passed in X.XXs ========================
```

- [ ] Oui, tous les tests passent! ✅✅✅

---

## Phase 7: Questions de compréhension (optionnel mais recommandé)

Réponds à ces questions pour vraiment comprendre:

### Question 1: 401 vs 403
**Q:** Quelle est la différence entre 401 et 403?

**Réponse attendue:**
- 401 = Pas d'authentification (pas de token ou token invalide)
- 403 = Authentifié mais pas les bons rôles

Toi:
```
Mon réponse: _________________________________________
```

### Question 2: @PreAuthorize
**Q:** À quoi sert l'annotation `@PreAuthorize("hasRole('ADMIN')")`?

**Réponse attendue:**
Elle vérifie (avant d'exécuter la méthode) que l'utilisateur authentifié a le rôle ADMIN. Si non, Spring retourne 403.

Toi:
```
Mon réponse: _________________________________________
```

### Question 3: Authentification requise
**Q:** Comment faire en sorte qu'un endpoint requière une authentification mais SANS rôle spécifique?

**Réponse attendue:**
Utiliser `@PreAuthorize("isAuthenticated()")` ou `@PreAuthorize("authenticated")`

Toi:
```
Mon réponse: _________________________________________
```

### Question 4: Récupérer l'utilisateur
**Q:** Comment récupérer le **username** de l'utilisateur actuellement connecté?

**Réponse attendue:**
```java
@GetMapping("/user")
public String getUsername(Authentication auth) {
    return auth.getName();  // ← C'est ça!
}
```

Toi:
```
Mon réponse: _________________________________________
```

### Question 5: Les rôles dans le token JWT
**Q:** Où sont encodés les rôles dans le token JWT de test?

**Réponse attendue:**
Dans le claim `"authorities": ["ROLE_ADMIN"]` créé par `TestAuthController.getTestToken()`

Toi:
```
Mon réponse: _________________________________________
```

---

## Phase 8: Défi bonus (pour les rapides)

Si tous les tests passent et tu as fini rapidement, essaie:

### Défi 1: Ajouter un endpoint user-specific
Créer `GET /farm/{username}` qui:
- Est protégé (authentification requise)
- Retourne la ferme d'un autre utilisateur
- MAIS seulement si:
  - L'utilisateur est ADMIN, OU
  - C'est sa propre ferme

Hint: Utiliser `hasRole('ADMIN')` ET `#username == authentication.name`

### Défi 2: Audit logging
Modifier `/farm/reset` pour logger:
- Qui a exécuté la commande (l'admin)
- Quand (timestamp)
- Ce qui a été réinitialisé

### Défi 3: Tests supplémentaires
Écrire 3 nouveaux tests pour:
- Tester qu'un endpoint public ne requiert pas d'auth
- Tester qu'un endpoint ADMIN-only refuse les USER
- Tester la réponse d'erreur à un token expiré

---

## 🎯 Résumé final

Si tu as des ✅ partout:

1. **Tu maîtrises la sécurité Spring Boot** ✅
2. **Tu comprends l'authentification et l'autorisation** ✅
3. **Tu sais utiliser les annotations Spring Security** ✅
4. **Tu peux protéger des endpoints en production** ✅

**Bravo! 🎉**

Prochaine étape: Implémenter l'authentification réelle (OAuth2 GitHub, ou JPA users).

---

## 📞 Aide

Si un test échoue:

```
FAILED test_admin_stats_returns_json_with_admin_token
AssertionError: 404 != 200
```

→ L'endpoint `/admin/stats` n'existe pas. Crée la méthode dans AdminController.

```
FAILED test_admin_stats_requires_admin_role
AssertionError: 200 != 403
```

→ L'endpoint ne vérifie pas les rôles. Ajoute `@PreAuthorize("hasRole('ADMIN')")`.

```
FAILED test_counter_view_is_public
AssertionError: 401 != 200
```

→ GET /counter ne devrait pas être protégé. Enlève l'annotation `@PreAuthorize` ou ajoute `.permitAll()` dans SecurityConfig.
