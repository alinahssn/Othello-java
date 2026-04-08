import java.util.Scanner;

public class MenuOthello {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choixnb;
        int niveau;

        System.out.println();
        System.out.println("▲● BIENVENUE SUR OTHELLO ▲●");

        do {

            System.out.println("\nMenu:");
            System.out.println(" 1. Afficher les règles du jeu Othello ");
            System.out.println(" 2. Jouer contre l'ordinateur");
            System.out.println(" 3. Jouer à 2");
            System.out.println(" 4. Quitter");


            System.out.print("Choisissez une option entre 1 et 4: ");
            choixnb = scanner.nextInt();

            switch (choixnb) {

                case 1:
                    System.out.print( "\n"+
                            "        ▲●  RÈGLES DU JEU OTHELLO  ●▲\n" +
                            "\n" +
                            "1) Au début du jeu, 4 pions sont placés au centre du plateau.\n" +
                            "2) Le joueur Noir (●) commence toujours la partie. C'est le joueur 1\n" +
                            "3) Les joueurs jouent chacun leur tour.\n" +
                            "4) Pour jouer, il faut poser un pion sur une case vide.\n" +
                            "5) Le pion posé doit coincer au moins un pion adverse entre deux pions de la même couleur.\n" +
                            "6) Les pions peuvent être coincés en ligne, en colonne ou en diagonale.\n" +
                            "7) Tous les pions coincés doivent être retournés automatiquement.\n" +
                            "8) Si un joueur ne peut retourner aucun pion, il passe son tour.\n" +
                            "9) Le jeu se termine quand le plateau est plein ou quand aucun joueur ne peut jouer.\n" +
                            "10)Si un joueur saisit 0 pour la ligne ou la colonne, il quitte la partie et retourne au menu.\n"+
                            "\n" +
                            " Le joueur qui a le plus de pions gagne la partie !\n" +
                            "\n");
                    break;

                case 2:
                    char[][] plateau = Plateau.CreationPlateau();
                    System.out.print("Tapez 1 pour le niveau facile ou 2 pour le niveau moyen: ");
                    niveau = scanner.nextInt();
                    if(niveau == 1){
                        Plateau.jeuContreOrdinateurFacile(Plateau.CreationPlateau());
                    } else if(niveau == 2){
                        Plateau.jeuContreOrdinateurMoyen(Plateau.CreationPlateau());
                    }
                    break;

                case 3:
                    Plateau.jeuDeuxJoueurs(Plateau.CreationPlateau());
                    break;

                case 4:
                    System.out.println("Quitter");
                    break;

                default:
                    System.out.println("Choix invalide !");
            }

        } while (choixnb != 4);

    }
}