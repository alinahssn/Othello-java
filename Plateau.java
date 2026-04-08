import java.util.Scanner;

public class Plateau {

    public static char[][] CreationPlateau() {


        char[][] plateau = new char[8][8];


        for (int ligne = 0; ligne < plateau.length; ligne++) {
            for (int col = 0; col < plateau[ligne].length; col++) {
                plateau[ligne][col] = '.';
            }
        }

        //N=● B=▲
        plateau[3][3] = 'N';
        plateau[3][4] = 'B';
        plateau[4][3] = 'B';
        plateau[4][4] = 'N';

        return plateau;
    }

    public static void afficherPlateau(char[][] plateau, String pseudoJoueur1, String pseudoJoueur2) {

        int pointsJoueur1 = 0;
        int pointsJoueur2 = 0;

        // Compte les pions sur le plateau à chaque affichage
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int col = 0; col < 8; col++) {

                if (plateau[ligne][col] == 'N') {
                    pointsJoueur1++;
                }
                else if (plateau[ligne][col] == 'B') {
                    pointsJoueur2++;
                }

            }
        }
        System.out.println("   "+"●"+ pseudoJoueur1 + " " + pointsJoueur1 + "         " +"▲"+ pseudoJoueur2 + " " + pointsJoueur2);


        System.out.println("   1  2  3  4  5  6  7  8");

        for (int ligne = 0; ligne < plateau.length; ligne++) {
            System.out.print((ligne + 1) + " ");
            for (int col = 0; col < plateau[ligne].length; col++) {

                char casePlateau = plateau[ligne][col];
                switch (casePlateau) {
                    case 'N':
                        System.out.print(" ● ");
                        break;
                    case 'B':
                        System.out.print(" ▲ "); //'' pour un caractère et "" pour plsrs
                        break;
                    default:
                        System.out.print(" " + casePlateau + " ");
                }
            }

            System.out.println();
        }
    }

    public static String[] pseudos2Joueurs() {
        Scanner scanner = new Scanner(System.in);
        String pseudoJoueur1;
        String pseudoJoueur2;

        do {
            System.out.print("Le joueur n°1 commencera la partie.");
            System.out.print("\nJoueur 1 entrez votre pseudo: ");
            pseudoJoueur1 = scanner.nextLine();
            System.out.print("Joueur 2 entrez votre pseudo: ");
            pseudoJoueur2 = scanner.nextLine();
            if (pseudoJoueur1.equals(pseudoJoueur2)) System.out.println("Veuillez saisir des pseudos différents");
        } while (pseudoJoueur1.equals(pseudoJoueur2));

        return new String[]{pseudoJoueur1, pseudoJoueur2};
    }

    public static String[] pseudo1joueur() {
        Scanner scanner = new Scanner(System.in);
        String pseudoJoueur1;
        String pseudoJoueur2 = "Ordinateur";

        System.out.print("Le joueur n°1 commencera la partie.");
        System.out.print("\nJoueur 1 entrez votre pseudo: ");
        pseudoJoueur1 = scanner.nextLine();

        return new String[]{pseudoJoueur1, pseudoJoueur2};//Meme principe qu'avec 2 pseudos
    }


    public static boolean interieurPlateau(int ligne, int col){
        return ligne >= 0 && ligne < 8 && col >= 0 && col < 8;
    }

    public static boolean plateauPlein(char[][] plateau) {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int col = 0; col < 8; col++) {
                if (plateau[ligne][col] == '.') {
                    return false; // il reste une case vide
                }
            }
        }
        return true; // plus aucune case vide
    }

    public static boolean veutQuitter(int ligne, int col) {
        if (ligne == 0 || col == 0) {
            System.out.println("Retour au menu...");
            return true; // Oui, le joueur veut quitter
        }
        return false; // Non, il continue
    }



    public static boolean coupValide (char[][] plateau, int ligne, int col, char joueur ) {
        if (!interieurPlateau(ligne, col) || plateauPlein(plateau) ||  plateau[ligne][col] != '.' ) {
            return false;
        }

        char adversaire = ' ';
        if (joueur == 'N') {
            adversaire = 'B';
        } else {
            adversaire = 'N';
        }

        int[][] directions = {
                {-1,-1}, {-1,0}, {-1,1},
                {0,-1},           {0,1},
                {1,-1},   {1,0},  {1,1}
        };

        for (int d = 0; d < directions.length; d++) {

            int dligne = directions[d][0];
            int dcol = directions[d][1];

            int i = ligne + dligne;
            int j = col + dcol;
            boolean trouveAdversaire = false;

            while (interieurPlateau(i, j) && plateau[i][j] == adversaire) {
                trouveAdversaire = true;
                i = i + dligne;
                j = j + dcol;
            }

            if (trouveAdversaire && interieurPlateau(i, j) && plateau[i][j] == joueur) {
                return true;
            }
        }
        return false;
    }



    public static void retournementPions(char [][] plateau, int ligne, int col, char joueur) {

        char adversaire = ' ';
        if (joueur == 'N') {
            adversaire = 'B';
        } else {
            adversaire = 'N';
        }

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
        };

        for (int d = 0; d < directions.length; d++) {
            int dl = directions[d][0];
            int dc = directions[d][1];

            int i = ligne + dl; //Placement sur le première case voisine
            int j = col + dc;
            boolean trouveAdversaire = false;// Le code ne rentre pas dans le if si pas d'adversaire entre deux pions

            while (interieurPlateau(i, j) && plateau[i][j] == adversaire) {
                i = i + dl;
                j = j + dc;
                trouveAdversaire = true;// le code rentre dans le if pour le retournement
            }

            if (trouveAdversaire && interieurPlateau(i, j) && plateau[i][j] == joueur) { //refermer le sandwich seulement si : on a sauté au moins un pion adverse, on n'est pas sorti du plateau et la case ou on s'est arreté contient le pion du joueur
                int reparcourirL = ligne + dl;
                int reparcourirC = col + dc;

                while (reparcourirL != i || reparcourirC != j) {
                    plateau[reparcourirL][reparcourirC] = joueur;
                    reparcourirL = reparcourirL + dl;
                    reparcourirC = reparcourirC + dc;
                }

            }
        }
    }
    // CHANGEMENTS : Vérification qu'on sort jamais du plateau en cherchant le spions adverse
    //               Le dernier while plus fiable qu'un do while pour s'arreter sur la case de fin
    public static boolean peutJouer (char [][] plateau, char joueur) {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int col = 0; col < 8; col++) {
                if (coupValide(plateau, ligne, col, joueur)) {
                    return true;
                }
            }
        }
        return false;
    }// Methode pour voir si les joueurs peuvent jouer ou doivent passer leurs tours


    public static int calculerGain (char [][] plateau, int ligne, int col, char joueur) {
        char adversaire = ' ';
        if (joueur == 'N') {
            adversaire = 'B';
        } else {
            adversaire = 'N';
        }
        int gainTotal = 0;

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
        };

        for (int d = 0; d < directions.length; d++) {
            int dl = directions[d][0];
            int dc = directions[d][1];

            int i = ligne + dl; //Placement sur le première case voisine dans la direction
            int j = col + dc;
            int gainDirection = 0; // compteur de jetons capturés

            while (interieurPlateau(i, j) && plateau[i][j] == adversaire) {
                gainDirection++;
                i = i + dl;// avance d'un pas dans la direction
                j = j + dc;
            }
            if (gainDirection > 0 && interieurPlateau(i, j) && plateau[i][j] == joueur) {
                gainTotal = gainTotal + gainDirection;
            }
        }
        return gainTotal;
    }

    public static void annonceGagnant (char [][] plateau, String pseudoJoueur1, String pseudoJoueur2){
        int pointsJoueur1 = 0;
        int pointsJoueur2 = 0;

        for (int ligne = 0; ligne < 8; ligne++) { //recompte pour etres surs
            for (int col = 0; col < 8; col++) {
                if (plateau[ligne][col] == 'N'){
                    pointsJoueur1++;
                } else if (plateau[ligne][col] == 'B'){
                    pointsJoueur2++;
                }
            }
        }

        if (pointsJoueur1 > pointsJoueur2) {
            System.out.println("Le/La gagnant(e) est " + pseudoJoueur1 + " avec " + pointsJoueur1 + " points !");
        }
        else if (pointsJoueur2 > pointsJoueur1) {
            System.out.println("Le/La gagnant(e) est " + pseudoJoueur2 + " avec " + pointsJoueur2 + " points !");
        }
        else {
            System.out.println("Match nul ! " + pointsJoueur1 + " points chacun !");
        }
    }



    public static void jeuDeuxJoueurs(char[][] plateau) {
        Scanner Scanner = new Scanner(System.in);
        int ligne;
        int col;
        String[] pseudos = pseudos2Joueurs();
        String pseudoJoueur1 = pseudos[0];
        String pseudoJoueur2 = pseudos[1];

        System.out.println();

        afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);

        while (!plateauPlein(plateau)) {
            if (!peutJouer(plateau, 'N') && !peutJouer(plateau, 'B')) { // Si aucun des deux joueurs à la possibilité de mettre un pions la partie est terminé
                System.out.println("Plus aucun coup possible pour les deux joueurs");
                break;
            }

            if (peutJouer(plateau, 'N')) {// Si le joueur1 peut poser un pions il joue sinon il passe son tour
                while (true) {
                    System.out.print("\n" + pseudoJoueur1+", choisis une ligne (1-8 ou 0 pour quitter) : ");
                    ligne = Scanner.nextInt();
                    System.out.print("Choisis une colonne (1-8 ou 0 pour quitter) : ");
                    col = Scanner.nextInt();

                    if (veutQuitter(ligne, col)) {
                        return; // Ce return quitte bien la méthode de JEU
                    }

                    ligne = ligne - 1; // On ajuste l'indice SEULEMENT après avoir vérifié le 0
                    col = col - 1;

                    if (!interieurPlateau(ligne, col)) {
                        System.out.println("Case hors plateau! Tu dois choisir entre 1 et 8 !");
                    } else if (plateau[ligne][col] != '.') {
                        System.out.println("Cette case est déjà occupée !");
                    } else if (!coupValide(plateau, ligne, col, 'N')) {
                        System.out.println("Coup non valide !");
                    } else {
                        break; // Sort de la boucle de saisie, le coup est valide on affiche le pion sur le plateau
                    }
                }
                plateau[ligne][col] = 'N';
                retournementPions(plateau, ligne, col, 'N');
                afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);
            } else {
                System.out.println(pseudoJoueur1 + " ne peut pas jouer et passe son tour");//passe son tour ne rentre pas dans le if et viens ici

            }

            if (plateauPlein(plateau))
                break; // la boucle verifie le remplissage du tableau au debut de chaque grand tour (joueur 1 et joueur 2) donc avec ça on verifie au millieu du tour

            if (peutJouer(plateau, 'B')) {
                while (true) {
                    System.out.print("\n" + pseudoJoueur2 +" , choisis une ligne (1-8 ou 0 pour quitter) : ");
                    ligne = Scanner.nextInt();
                    System.out.print("Choisis une colonne (1-8 ou 0 pour quitter) : ");
                    col = Scanner.nextInt();

                    if (veutQuitter(ligne, col)) {
                        return;
                    }

                    ligne = ligne - 1;
                    col = col - 1;

                    if (!interieurPlateau(ligne, col)) {
                        System.out.println("Case hors plateau! Tu dois choisir entre 1 et 8 !");
                    } else if (plateau[ligne][col] != '.') {
                        System.out.println("Cette case est déjà occupée !");
                    } else if (!coupValide(plateau, ligne, col, 'B')) {
                        System.out.println("Coup non valide !");
                    } else {
                        break; // Sort de la boucle de saisie, le coup est valide on affiche le pion sur le plateau
                    }
                }
                plateau[ligne][col] = 'B';
                retournementPions(plateau, ligne, col, 'B');
                afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);
            } else {
                System.out.println(pseudoJoueur2 + " ne peut pas jouer  et passe son tour");
            }
        }

        System.out.println("Fin de la partie !");
        annonceGagnant(plateau, pseudoJoueur1, pseudoJoueur2);

    }


    public static void jeuContreOrdinateurFacile(char[][] plateau) {
        Scanner Scanner = new Scanner(System.in);
        int ligne;
        int col;
        String[] pseudos = pseudo1joueur();
        String pseudoJoueur1 = pseudos[0];
        String pseudoJoueur2 = pseudos[1];

        System.out.println();

        afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);

        while(!plateauPlein(plateau)) {
            if (!peutJouer(plateau, 'N') && !peutJouer(plateau, 'B')) {
                System.out.println("Plus aucuns coup possible pour les deux joueurs.");
                break;
            }

            if (peutJouer(plateau, 'N')) {
                while (true) {
                    System.out.print("\n" + pseudoJoueur1+", choisis une ligne (1-8 ou 0 pour quitter) : ");
                    ligne = Scanner.nextInt();
                    System.out.print("Choisis une colonne (1-8 ou 0 pour quitter) : ");
                    col = Scanner.nextInt();

                    if (veutQuitter(ligne, col)) {
                        return; // Ce return quitte bien la méthode de JEU
                    }

                    ligne = ligne - 1; // On ajuste l'indice SEULEMENT après avoir vérifié le 0
                    col = col - 1;

                    if (!interieurPlateau(ligne, col)) {
                        System.out.println("Case hors plateau! Tu dois choisir entre 1 et 8 !");
                    } else if (plateau[ligne][col] != '.') {
                        System.out.println("Cette case est déjà occupée !");
                    } else if (!coupValide(plateau, ligne, col, 'N')) {
                        System.out.println("Coup non valide !");
                    } else {
                        break; // Sort de la boucle de saisie, le coup est valide on affiche le pion sur le plateau
                    }
                }
                plateau[ligne][col] = 'N';
                retournementPions(plateau, ligne, col, 'N');
                afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);
            } else {
                System.out.println(pseudoJoueur1 + " ne peut pas jouer et passe son tour");
            }


            if (plateauPlein(plateau)) break;

            System.out.println("\n L'ordinateur réfléchit...");//balayage du plateau pour trouver une case ok

            int ligneChoisie = -1;
            int colChoisie = -1;
            boolean coupTrouve = false;

            for (ligne = 0; ligne < 8; ligne++) {
                for (col = 0; col < 8; col++) {
                    if (plateau[ligne][col] == '.' && coupValide(plateau, ligne, col, 'B')) {
                        ligneChoisie = ligne;
                        colChoisie = col;
                        coupTrouve = true;
                        break;  //Coup Trouvé
                    }
                }
                if (coupTrouve) {
                    break;
                }
            }

            if (coupTrouve) {
                System.out.println("l'ordinateur joue en : " + (ligneChoisie + 1) + "," + (colChoisie + 1));
                plateau[ligneChoisie][colChoisie] = 'B';
                retournementPions(plateau, ligneChoisie, colChoisie, 'B');
                afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);
            } else {
                System.out.println("L'ordinateur ne peut pas jouer et passe son tour.");
            }


        }

        System.out.println("Fin de la partie !");
        annonceGagnant(plateau, pseudoJoueur1, pseudoJoueur2);

    }


    public static void jeuContreOrdinateurMoyen(char[][] plateau) {
        Scanner Scanner = new Scanner(System.in);
        int ligne;
        int col;
        String[] pseudos = pseudo1joueur();
        String pseudoJoueur1 = pseudos[0];
        String pseudoJoueur2 = pseudos[1];

        System.out.println();

        afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);

        while(!plateauPlein(plateau)) {
            if (!peutJouer(plateau, 'N') && !peutJouer(plateau, 'B')) {
                System.out.println("Plus aucuns coup possible pour les deux joueurs.");
                break;
            }

            if (peutJouer(plateau, 'N')) {
                while (true) {
                    System.out.print("\n" + pseudoJoueur1+", choisis une ligne (1-8 ou 0 pour quitter) : ");
                    ligne = Scanner.nextInt();
                    System.out.print("Choisis une colonne (1-8 ou 0 pour quitter) : ");
                    col = Scanner.nextInt();

                    if (veutQuitter(ligne, col)) {
                        return; // Ce return quitte bien la méthode de JEU
                    }

                    ligne = ligne - 1; // On ajuste l'indice SEULEMENT après avoir vérifié le 0
                    col = col - 1;

                    if (!interieurPlateau(ligne, col)) {
                        System.out.println("Case hors plateau! Tu dois choisir entre 1 et 8 !");
                    } else if (plateau[ligne][col] != '.') {
                        System.out.println("Cette case est déjà occupée !");
                    } else if (!coupValide(plateau, ligne, col, 'N')) {
                        System.out.println("Coup non valide !");
                    } else {
                        break; // Sort de la boucle de saisie, le coup est valide on affiche le pion sur le plateau
                    }
                }
                plateau[ligne][col] = 'N';
                retournementPions(plateau, ligne, col, 'N');
                afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);
            } else {
                System.out.println(pseudoJoueur1 + " ne peut pas jouer et passe son tour");
            }


            if (plateauPlein(plateau)) break;

            System.out.println("\n L'ordinateur réfléchit stratégiquement...");//balayage du plateau pour trouver une case ok

            int ligneChoisie = -1;
            int colChoisie = -1;
            int meilleurGain = 0;
            boolean coupTrouve = false;

            for (ligne = 0; ligne < 8; ligne++) {
                for (col = 0; col < 8; col++) {
                    if (plateau[ligne][col] == '.' && coupValide(plateau, ligne, col, 'B')) {

                        int gainActuel = calculerGain(plateau, ligne, col, 'B');

                        // STRATÉGIE : Si ce coup rapporte plus que le record actuel
                        if (gainActuel > meilleurGain) {
                            meilleurGain = gainActuel; // On met à jour le nouveau record
                            ligneChoisie = ligne;          // On mémorise la ligne
                            colChoisie = col;            // On mémorise la colonne
                            coupTrouve = true;
                        }
                    }
                }
            }

            // Une fois qu'on a fini de tout scanner, on joue le meilleur coup
            if (coupTrouve) {
                System.out.println("L'ordinateur a trouvé un coup à " + meilleurGain + " points !");
                System.out.println("Il joue en : " + (ligneChoisie + 1) + "," + (colChoisie + 1));

                plateau[ligneChoisie][colChoisie] = 'B';
                retournementPions(plateau, ligneChoisie, colChoisie, 'B');
                afficherPlateau(plateau, pseudoJoueur1, pseudoJoueur2);
            } else {
                System.out.println("L'ordinateur ne peut pas jouer et passe son tour.");
            }
        }

        System.out.println("Fin de la partie !");
        annonceGagnant(plateau, pseudoJoueur1, pseudoJoueur2);

    }

}
//return : Quitte toute la méthode (on arrête tout et on repart au menu).
//break : Quitte seulement la boucle actuelle (on finit la partie et on affiche les points).

