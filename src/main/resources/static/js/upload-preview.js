document.addEventListener('DOMContentLoaded', () => {
    function setupUploadPreview(inputId, thumbnailId, errorMsgId) {
    const fileInput = document.getElementById(inputId);
    const thumbnail = document.getElementById(thumbnailId);
    const errorMsg = document.getElementById(errorMsgId);

    // Vérifie qu'il y ait bien l'input d'image
    if (!fileInput) return;

        //Surveille le moment où le user a fini de choisir son image
        fileInput.addEventListener('change', function() {
            //Obligatoire car JS considère toujours qu'un input de type file est un tableau
            const file = this.files[0];

            if (file) {
                // Valide la limite de taille de 1Mo
                if (file.size > 1048576) {
                    //Si erreur enleve hidden du message d'erreur
                    if (errorMsg) errorMsg.classList.remove('hidden');
                    // Recache la prévisualisation (au cas où une image valide était affichée juste avant)
                    if (thumbnail) thumbnail.style.display = 'none';
                    //Reset l'input (évite de garder l'image trop lourde dedans)
                    this.value = "";
                } else {
                    if (errorMsg) errorMsg.classList.add('hidden');

                    if (thumbnail) {
                        /* Créer une URL temporaire vers l'image (stockée dans la mémoire du navigateur) pour afficher
                        la prévisualisation sans envoyer quoi que ce soit au serveur */
                        thumbnail.src = URL.createObjectURL(file);
                        thumbnail.style.display = 'block';
                    }
                }
            }
        });
    }
    //Mobile
    setupUploadPreview('fileImage_Mobile', 'thumbnail_Mobile', 'imageError_Mobile');

    //Desktop
    setupUploadPreview('fileImage_Desktop', 'thumbnail_Desktop', 'imageError_Desktop');
});