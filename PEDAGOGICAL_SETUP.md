# 🎓 Jeu de Tests Pédagogique - Sécurité Spring Boot

## 📖 Vue d'ensemble

Ce projet contient un **jeu de tests intentionnellement non réussis** pour enseigner la sécurité dans Spring Boot.

**Concept:** Les tests sont **justes et corrects**, mais l'application Spring manque d'implémentations. Les étudiants doivent modifier le code Java pour faire passer les tests.

---

## 🚀 Pour les étudiants

### Setup initial

```bash
# 1. Cloner et installer
git clone <repo>
cd simple-oauth
mvn clean install

# 2. Démarrer l'app Spring
mvn spring-boot:run

# 3. Dans un autre terminal, lancer les tests
pytest tests/test_security_exercise.py -v
```

### Objectif

Faire passer **tous les tests** en modifiant le code Spring Boot.

**Fichiers à modifier:**
- `src/main/java/com/example/authgithub/controller/AdminController.java`
- `src/main/java/com/example/authgithub/controller/FarmController.java`
- `src/main/java/com/example/authgithub/controller/CounterController.java`

**À faire:**
1. Lire les tests dans `tests/test_security_exercise.py`
2. Comprendre ce qu'ils testent
3. Implémenter les endpoints manquants avec les annotations de sécurité appropriées
4. Lancer les tests jusqu'à ce qu'ils passent tous ✅

---

## 🎯 Learning Outcomes

Après avoir complété cet exercice, l'étudiant comprendra:

✅ La différence entre authentification (401) et autorisation (403)
✅ Comment utiliser `@PreAuthorize` pour protéger les endpoints
✅ Les rôles (ROLE_ADMIN, ROLE_USER) et leur attribution
✅ Comment accéder à l'utilisateur actuel via `Authentication`
✅ La structure d'une securité Spring bien configurée

---

## 👨‍🏫 Pour les profs / mentors

### Fichiers clés

| Fichier | Purpose |
|---------|---------|
| `tests/test_security_exercise.py` | **Les tests** (justes, ne pas modifier) |
| `SECURITY_EXERCISE.md` | Guide pédagogique pour les étudiants |
| `SOLUTION.md` | Solution complète (pour la correction) |

### Comment utiliser ce jeu

1. **Donner aux étudiants:**
   - Le code initial (avec endpoints incomplets)
   - `tests/test_security_exercise.py`
   - `SECURITY_EXERCISE.md`

2. **Dire aux étudiants:**
   > "Tous les tests sont corrects. Modifiez le code Spring pour les faire passer. Vous apprendrez la sécurité en implémentant."

3. **Lors de la correction:**
   - Consulter `SOLUTION.md`
   - Vérifier que les endpoints ont los bonnes annotations
   - Discuter des choix (pourquoi 401 vs 403, etc.)

### Variantes pédagogiques

**Version 1: Exercice complet** (Difficile)
- Donner les tests
- Dire que les endpoints manquent
- Les étudiants implémentent tout

**Version 2: Avec scaffold** (Moyen)
- Donner les stubs des endpoints
- Les étudiants ajoutent les annotations de sécurité

**Version 3: Debugging** (Facile)
- Donner du code "en travers" (mauvaises annotations)
- Les étudiants réparent les bugs de sécurité

---

## 🧪 Commandes utiles

```bash
# Lancer TOUS les tests de sécurité
pytest tests/test_security_exercise.py -v

# Lancer UN test spécifique
pytest tests/test_security_exercise.py::TestAdminEndpoints::test_admin_stats_requires_auth -v

# Voir les détails des erreurs
pytest tests/test_security_exercise.py -vvv --tb=short

# Avec output en temps réel
pytest tests/test_security_exercise.py -v -s
```

---

## 📋 Checklist pour les étudiants

- [ ] J'ai lu `SECURITY_EXERCISE.md`
- [ ] J'ai lancé `mvn spring-boot:run`
- [ ] J'ai lancé `pytest tests/test_security_exercise.py -v`
- [ ] Je comprends quels tests failent et pourquoi
- [ ] J'ai créé/modifié `AdminController.java`
- [ ] J'ai ajouté les endpoints manquants à `FarmController.java`
- [ ] J'ai mis les annotations `@PreAuthorize` correctes
- [ ] Tous les tests passent ✅
- [ ] Je comprends la différence 401 vs 403
- [ ] Je peux expliquer comment `@PreAuthorize` fonctionne

---

## 💡 Tips de débogage

Si un test échoue:

1. **Lire le message d'erreur**
   ```bash
   AssertionError: Le endpoint /admin/stats ne protège pas l'accès anonyme
   ```
   → Ajouter `@PreAuthorize("hasRole('ADMIN')")`

2. **Vérifier le status HTTP**
   - 404 → endpoint n'existe pas → créer la méthode
   - 401 → pas d'authentification requise → ajouter `@PreAuthorize("isAuthenticated()")`
   - 403 → rôles insuffisants → ajouter `@PreAuthorize("hasRole('...')")`
   - 500 → erreur serveur → regarder les logs

3. **Regarder les logs Spring**
   ```
   org.springframework.security.access.AccessDeniedException: ...
   ```

4. **Vérifier les imports**
   ```java
   import org.springframework.security.access.prepost.PreAuthorize;
   import java.util.Map;
   ```

---

## 🔗 Fichiers connexes

- [Sécurité Spring Doc](https://spring.io/projects/spring-security)
- [JWT avec Spring Security](https://spring.io/blog/2021/06/04/spring-security-without-the-servlet-api)
- [PreAuthorize Annotation](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html)

---

## 📞 FAQ

**Q: Pourquoi les tests sont-ils dans `tests/` (Python) et pas en Java?**
A: Parce qu'ça teste les endpoints HTTP, pas la logique interne. Les tests HTTP sont plus faciles avec Python + requests.

**Q: Je ne comprends pas `@PreAuthorize`?**
A: C'est une annotation qui dit "vérifie les permissions AVANT d'exécuter cette méthode". Si l'utilisateur n'a pas les bons rôles, Spring retourne 403.

**Q: Comment savoir si j'ai besoin de `hasRole('ADMIN')` ou juste `isAuthenticated()`?**
A: Regarder le test!
- Si le test attend 401 sans token → `isAuthenticated()`
- Si le test expect 403 avec un token NON-ADMIN → `hasRole('ADMIN')`

**Q: Et si je break les autres endpoints en modifiant les fichiers?**
A: Vérifier que `SecurityConfig.java` autorise les bonnes routes: `/`, `/counter` (GET), `/auth/**`.

---

## ✅ Succès!

Tous les tests passent?

```
======================== 13 passed in 2.34s ========================
```

**Bravo! 🎉** Vous maîtrisez maintenant la sécurité Spring Boot.

Prochaine étape: Implémenter OAuth2 avec GitHub et JPA pour persister les utilisateurs.
