#!/bin/bash
# 🔐 Gestionnaire de fichier prof: chiffrement / déchiffrement
# Usage: 
#   ./manage_teacher_files.sh encrypt <password>
#   ./manage_teacher_files.sh decrypt <password>

set -e

SOLUTION_FILE="SOLUTION.md"
QUICK_REF_FILE="QUICK_REFERENCE.md"
ENCRYPTED_FILE="SOLUTION.md.enc"
ENCRYPTED_QUICK="QUICK_REFERENCE.md.enc"

encrypt() {
    local password="$1"
    
    if [ -z "$password" ]; then
        echo "❌ Erreur: fournir un mot de passe"
        echo "Usage: $0 encrypt <password>"
        exit 1
    fi
    
    if [ ! -f "$SOLUTION_FILE" ]; then
        echo "❌ Fichier $SOLUTION_FILE non trouvé"
        exit 1
    fi
    
    echo "🔐 Chiffrement de $SOLUTION_FILE..."
    openssl enc -aes-256-cbc -salt -in "$SOLUTION_FILE" -out "$ENCRYPTED_FILE" -k "$password"
    
    echo "✅ Fichier chiffré: $ENCRYPTED_FILE"
    echo "📝 Vous pouvez maintenant supprimer $SOLUTION_FILE avec: rm $SOLUTION_FILE"
}

decrypt() {
    local password="$1"
    
    if [ -z "$password" ]; then
        echo "❌ Erreur: fournir un mot de passe"
        echo "Usage: $0 decrypt <password>"
        exit 1
    fi
    
    if [ ! -f "$ENCRYPTED_FILE" ]; then
        echo "❌ Fichier $ENCRYPTED_FILE non trouvé"
        exit 1
    fi
    
    echo "🔓 Déchiffrement de $ENCRYPTED_FILE..."
    openssl enc -aes-256-cbc -d -salt -in "$ENCRYPTED_FILE" -out "$SOLUTION_FILE" -k "$password"
    
    if [ $? -eq 0 ]; then
        echo "✅ Fichier déchiffré: $SOLUTION_FILE"
        echo "⚠️  Ce fichier est maintenant VISIBLE dans le repo"
    else
        echo "❌ Mot de passe incorrect ou fichier corrompu"
        exit 1
    fi
}

show_help() {
    echo "🔐 Gestionnaire de fichiers prof"
    echo ""
    echo "Commandes disponibles:"
    echo "  encrypt <password>    Chiffrer SOLUTION.md"
    echo "  decrypt <password>    Déchiffrer SOLUTION.md.enc"
    echo ""
    echo "Exemple:"
    echo "  ./manage_teacher_files.sh encrypt 'MySecurePassword123!'"
    echo "  ./manage_teacher_files.sh decrypt 'MySecurePassword123!'"
}

case "${1:-}" in
    encrypt)
        encrypt "$2"
        ;;
    decrypt)
        decrypt "$2"
        ;;
    *)
        show_help
        exit 1
        ;;
esac
