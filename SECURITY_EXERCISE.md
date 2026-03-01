# Exercice Sécurité Spring Boot - Guide d'implémentation

## 🎮 Objectif
Les tests dans `tests/test_security_exercise.py` sont **intentionnellement non réussis**. Ton objectif est de modifier le code Spring pour les faire passer.

Chaque test est **correct et juste**. C'est à toi d'implémenter la sécurité.

---

## 📋 Tâches à accomplir

### 1. **Créer l'endpoint `/admin/stats` (ADMIN-ONLY)**

**Tests concernés:**
- `test_admin_stats_requires_auth` ❌
- `test_admin_stats_requires_admin_role` ❌
- `test_admin_stats_returns_json_with_admin_token` ❌

**À faire:**
1. Créer une nouvelle méthode dans `UserController.java` (ou `AdminController.java`):
```java
@GetMapping("/admin/stats")
@PreAuthorize("hasRole('ADMIN')")  // 🔥 Important!
public Map<String, Object> adminStats() {
    return Map.of(
        "total_farms", 1,
        "total_users", 1
    );
}
```

2. La méthode doit:
   - Être protégée par `@PreAuthorize("hasRole('ADMIN')")`
   - Retourner un JSON avec `total_farms` et `total_users` (entiers)
   - Refuser l'accès si pas authentifié (401)
   - Refuser l'accès si pas ADMIN (403)

---

### 2. **Créer l'endpoint `POST /farm/reset` (ADMIN-ONLY)**

**Tests concernés:**
- `test_farm_reset_requires_admin` ❌
- `test_farm_reset_works_with_admin_token` ❌

**À faire:**
1. Ajouter dans `FarmController.java`:
```java
@PostMapping("/reset")
@PreAuthorize("hasRole('ADMIN')")
public Map<String, Object> resetAllFarms() {
    // Réinitialise toutes les fermes
    // ou au moins retourner un message
    return Map.of("message", "All farms have been reset");
}
```

---

### 3. **Créer l'endpoint `DELETE /farm` (Protégé)**

**Tests concernés:**
- `test_farm_delete_requires_auth` ❌
- `test_farm_delete_with_valid_token` ❌

**À faire:**
1. Ajouter une méthode DELETE dans `FarmController.java`:
```java
@DeleteMapping
@PreAuthorize("isAuthenticated()")
public Map<String, Object> deleteFarm(Authentication auth) {
    // Supprimer la ferme de l'utilisateur actuel
    // Utiliser auth.getName() pour récupérer le username
    return Map.of("message", "Farm deleted successfully");
}
```

---

### 4. **Vérifier les configurations existantes**

✅ `/counter/increment` doit refuser les non-authentifiés
- Ajouter `@PreAuthorize("isAuthenticated()")` sur la méthode POST
- Ou ajouter dans `SecurityConfig` si pas déjà là

✅ `/counter` (GET) doit être publique
- Laisser comme is ou ajouter `.permitAll()` dans SecurityConfig

---

## 🔑 Concepts clés à comprendre

| Concept | Faire |
|---------|-------|
| **Authentification obligatoire** | `@PreAuthorize("isAuthenticated()")` ou `.authenticated()` |
| **Rôles requis** | `@PreAuthorize("hasRole('ADMIN')")` |
| **Accès public** | `.permitAll()` ou pas de `@PreAuthorize` |
| **Récupérer l'utilisateur** | `Authentication auth` en paramètre => `auth.getName()` |
| **Erreur 401** | Pas de token ou token invalide |
| **Erreur 403** | Token valide mais rôle insuffisant |

---

## 🧪 Comment lancer les tests

```bash
# Lancer tous les tests de sécurité
pytest tests/test_security_exercise.py -v

# Lancer un test spécifique
pytest tests/test_security_exercise.py::TestAdminEndpoints::test_admin_stats_requires_auth -v

# Voir les détails des erreurs
pytest tests/test_security_exercise.py -v --tb=short
```

---

## 📝 Checklist d'implémentation

- [ ] Endpoint `/admin/stats` créé et sécurisé
- [ ] Endpoint `POST /farm/reset` créé et ADMIN-ONLY
- [ ] Endpoint `DELETE /farm` créé et protégé
- [ ] `POST /counter/increment` protégé (authentification requise)
- [ ] `GET /counter` accessible publiquement
- [ ] Tous les tests de `test_security_exercise.py` passent ✅
- [ ] Comprendre la différence entre 401 et 403

---

## 💡 Tips

1. **Redémarrer l'app** après chaque modification
2. Utiliser `@PreAuthorize` sur les méthodes (plus simple)
3. Les erreurs de sécurité retournent automatiquement 401 ou 403 si bien configuré
4. Le JWT de test a toujours `username = "test-admin"` et `ROLE_ADMIN`

---

## Questions de compréhension

Après avoir réussi les tests, demande-toi:
1. Pourquoi 401 vs 403? Quelle est la différence?
2. Comment `@PreAuthorize` interagit avec `SecurityConfig.authorizeHttpRequests()`?
3. Qui définit les rôles du token? (Réponse: `TestAuthController.getTestToken()`)
4. Comment récuper le nom de l'utilisateur actuel en Java?
