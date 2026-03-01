#!/bin/bash
# 🧪 Script de test rapide - Vérifier que l'exercice fonctionne

echo "🧪 TEST DE L'EXERCICE"
echo "===================="
echo ""
echo "1️⃣  Recompilation du code Java..."
mvn clean package -DskipTests -q

if [ $? -ne 0 ]; then
    echo "❌ Erreur de compilation"
    exit 1
fi

echo "✅ Compilation OK"
echo ""

echo "2️⃣  Démarrage de l'application Spring Boot en arrière-plan..."
java -jar target/*.jar > /tmp/spring-boot.log 2>&1 &
SPRING_PID=$!
echo "   PID Spring Boot: $SPRING_PID"

# Attendre que l'app démarre
echo "   Attente du démarrage (20 secondes)..."
sleep 20

echo ""
echo "3️⃣  Lancement des tests d'exercice..."
echo "   (Les tests DOIVENT échouer car les annotations manquent)"
echo ""

pytest tests/test_security_exercise.py -v --tb=short

TEST_RESULT=$?

echo ""
echo "4️⃣  Arrêt de l'application Spring Boot..."
kill $SPRING_PID 2>/dev/null
sleep 2

echo ""
echo "📊 RÉSUMÉ"
echo "========="
if [ $TEST_RESULT -ne 0 ]; then
    echo "✅ Parfait! Des tests ÉCHOUENT (comme attendu)"
    echo "   → L'exercice est prêt pour les étudiants"
    echo ""
    echo "Tests qui doivent échouer:"
    echo "  ❌ test_admin_stats_requires_admin_role"
    echo "  ❌ test_farm_reset_requires_admin"
    echo "  ❌ test_farm_delete_requires_auth"
    echo "  ❌ etc."
    echo ""
    echo "Ces tests passeront ✅ quand les étudiants ajouteront:"
    echo "  - @PreAuthorize(\"hasRole('ADMIN')\")"
    echo "  - @PreAuthorize(\"isAuthenticated()\")"
else
    echo "⚠️  Tous les tests PASSENT"
    echo "   → Les solutions sont encore dans le code?"
    echo "   → Vérifier AdminController et FarmController"
fi

echo ""
echo "Pour lancer manuellement:"
echo "  mvn spring-boot:run &"
echo "  sleep 20"
echo "  pytest tests/test_security_exercise.py -v"
