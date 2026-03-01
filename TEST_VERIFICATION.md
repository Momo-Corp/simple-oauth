# 🧪 Tester que l'exercice fonctionne

## Test rapide (recommandé)

```bash
chmod +x test_exercise.sh
./test_exercise.sh
```

Le script va:
1. Recompiler le code
2. Démarrer Spring Boot
3. Lancer les tests
4. Afficher les résultats

**Résultat attendu:** ❌ Environ 8-10 tests doivent ÉCHOUER

---

## Test manuel (étape par étape)

### Terminal 1: Démarrer Spring Boot
```bash
mvn spring-boot:run
```
Attendre le message: `Started AuthGithubApplication in X seconds`

### Terminal 2: Lancer les tests
```bash
pytest tests/test_security_exercise.py -v
```

---

## 📊 Résultats attendus

### Tests qui DOIVENT échouer ❌
```
FAILED test_admin_stats_requires_admin_role
  → Car @PreAuthorize("hasRole('ADMIN')") manque
  
FAILED test_farm_reset_requires_admin
  → Car @PreAuthorize("hasRole('ADMIN')") manque
  
FAILED test_farm_delete_requires_auth
  → Car @PreAuthorize("isAuthenticated()") manque
  
FAILED test_farm_reset_works_with_admin_token
  → Car endpoint pas protégé
  
FAILED test_farm_delete_with_valid_token
  → Car endpoint pas protégé
```

### Tests qui DOIVENT passer ✅
```
PASSED test_counter_view_is_public
  → Car GET /counter est bien public
  
PASSED test_counter_increment_requires_role
  → Car POST /counter/increment a @PreAuthorize
  
PASSED test_expired_or_invalid_token_returns_401
  → Car SecurityConfig OK
  
PASSED test_missing_auth_header_returns_401
  → Car SecurityConfig OK
```

---

## 🎯 Si tous les tests passent (BAD)

Ça veut dire que les solutions sont encore dans le code. Vérifier:

```bash
# Vérifier AdminController
grep -n "PreAuthorize" src/main/java/com/example/authgithub/controller/AdminController.java

# Doit montrer:
# 16:@PreAuthorize("isAuthenticated()")  ← Ligne de classe (OK)
# 32:    // TODO: Ajouter @PreAuthorize("hasRole('ADMIN')") ici !  ← Commentaire (OK)
# PAS de ligne active @PreAuthorize sur la méthode adminStats

# Vérifier FarmController
grep -n "PreAuthorize" src/main/java/com/example/authgithub/controller/FarmController.java

# Doit montrer des TODO en commentaire seulement
```

---

## 🎯 Si ~8-10 tests échouent (GOOD ✅)

**Parfait!** L'exercice est prêt:

1. ✅ Le code compile
2. ✅ Spring Boot démarre
3. ✅ Les tests détectent les problèmes de sécurité
4. ✅ Les étudiants doivent ajouter les annotations

**Prochaine étape:** Commit et push!

```bash
git status
git add src/main/java/com/example/authgithub/controller/
git commit -m "🎓 Retirer solutions pour créer exercice"
git push
```

---

## 🐛 Troubleshooting

**"Application ne démarre pas"**
```bash
mvn clean install
mvn spring-boot:run
```

**"Tests introuvables"**
```bash
ls -la tests/test_security_exercise.py
pip install pytest requests
```

**"Connection refused sur localhost:8080"**
→ Spring Boot n'est pas démarré ou pas fini de démarrer
→ Attendre 20-30 secondes après le lancement

---

## 📋 Checklist finale

- [ ] Spring Boot démarre sans erreur
- [ ] `pytest tests/test_security_exercise.py -v` échoue sur ~8-10 tests
- [ ] Les tests qui échouent sont liés à la sécurité (auth/admin)
- [ ] Les fichiers Java n'ont PAS de `@PreAuthorize` sur les méthodes exercées
- [ ] SOLUTION.md est chiffré (SOLUTION.md.enc existe)
- [ ] Le code est commité

**Si tout est ✅ → L'exercice est prêt pour les étudiants!** 🎓
