

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OthelloTest {

    @Test
    public void InterieurPlateau() {

        assertTrue(Plateau.interieurPlateau(0, 0), "cas 0,0");
        assertTrue(Plateau.interieurPlateau(7, 7), "cas 7,7");
        assertTrue(Plateau.interieurPlateau(3, 5), "cas 3,5");
        assertTrue(Plateau.interieurPlateau(1, 7), "cas 1,7");
        assertFalse(Plateau.interieurPlateau(-1, 0), "ligne negative");
        assertFalse(Plateau.interieurPlateau(100, 8), "cas ligne et colonne pas dans le tableau");
        assertFalse(Plateau.interieurPlateau(8, 8), "ligne et colonne pas dans le tableau");
    }

    @Test
    public void CoupValide() {
        char[][] plateau = Plateau.CreationPlateau();

        assertTrue(Plateau.coupValide(plateau, 3, 5, 'N'), "case coup valide pour les 'N'");
        assertFalse(Plateau.coupValide(plateau, 3, 3, 'N'),"Une case qui est déjà occupée");
        assertFalse(Plateau.coupValide(plateau, -10, 14, 'N'), "ligne et colonne hors plateau");
        assertTrue(Plateau.coupValide(plateau, 2, 3, 'B'), "case coup valide pour les 'B'");
        assertFalse(Plateau.coupValide(plateau, 4, 3, 'B'), "case occupée");
        assertFalse(Plateau.coupValide(plateau, 0, 0, 'B'), "aucun pion adverse autour");
        assertFalse(Plateau.coupValide(plateau, -66, 6, 'N'), "ligne hors plateau");
        assertFalse(Plateau.coupValide(plateau, 2, 14, 'N'), "colonne hors plateau");

        char[][] plateauPlein = new char[8][8];

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                plateauPlein[i][j] = 'B';

        assertFalse(Plateau.coupValide(plateauPlein, 6, 4, 'B'), "aucun coup valide sur un plateau plein");
        assertFalse(Plateau.coupValide(plateauPlein, 7, 3, 'N'), "coup invalide ");
    }

    @Test
    public void PlateauPlein() {
        char[][] plateauInitial = Plateau.CreationPlateau();
        assertFalse(Plateau.plateauPlein(plateauInitial), "cas plateau pas rempli");

        char[][] plateauPlein = new char[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                plateauPlein[i][j] = 'N';
        assertTrue(Plateau.plateauPlein(plateauPlein), "cas plateau rempli"); // on peut faire la même chose avec un plateau rempli de 'N' ou un plateau de 'N' et 'B' ou encore avec un tableau presque plein
    }

    @Test
   public  void RetournementPions() {
        char[][] plateauPourCasVertical = Plateau.CreationPlateau();
        Plateau.retournementPions(plateauPourCasVertical, 2, 3, 'N');
        assertEquals('N', plateauPourCasVertical[3][3], "cas retournement pion 3,3");

        char[][] plateauPourCasHorizontale = Plateau.CreationPlateau();
        plateauPourCasHorizontale [3][2] = 'N';
        plateauPourCasHorizontale [3][3] = 'B';
        plateauPourCasHorizontale [3][4] = '.';
        Plateau.retournementPions(plateauPourCasHorizontale , 3, 4, 'N');
        assertEquals('N', plateauPourCasHorizontale [3][3], "retournement horizontal");

        char[][] plateauPourDiagonalAutreCas = Plateau.CreationPlateau();
        plateauPourDiagonalAutreCas[2][2] = 'N';
        plateauPourDiagonalAutreCas[3][3] = 'B';
        plateauPourDiagonalAutreCas[4][4] = '.';

        Plateau.retournementPions(plateauPourDiagonalAutreCas, 4, 4, 'N');

        assertEquals('N', plateauPourDiagonalAutreCas[3][3], "retournement en diagonal du pion en 3,3 ");

        char[][] plateauPourCasDiagonal = Plateau.CreationPlateau();
        plateauPourCasDiagonal[3][3] = 'B';
        plateauPourCasDiagonal[4][4] = '.';

        Plateau.retournementPions(plateauPourCasDiagonal, 4, 4, 'N');

        assertEquals('B', plateauPourCasDiagonal[3][3], "retournement en diagonal du pion en 3,3 ne peut pas se faire");
    }

    @Test
    public void peutJouer() {
        char[][] plateau = Plateau.CreationPlateau();
        assertTrue(Plateau.peutJouer(plateau, 'N'), "joueur 'N' peut jouer");
        assertTrue(Plateau.peutJouer(plateau, 'B'), "joueur 'B' peut jouer");

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                plateau[i][j] = 'N';
        assertFalse(Plateau.peutJouer(plateau, 'B'), "joueur 'B' ne peut plus jouer");

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                plateau[i][j] = 'B';
        assertFalse(Plateau.peutJouer(plateau, 'N'), "joueur 'N' ne peut plus jouer");


    }

    @Test
    public void veutQuitter() {

        assertTrue(Plateau.veutQuitter(0, 6), "cas ligne egale à 0 donc veut quitter");
        assertTrue(Plateau.veutQuitter(7, 0),"cas colonne egale à 0 donc veut quitter");
        assertTrue(Plateau.veutQuitter(0, 0),"cas ligne et colonne egale à 0 donc veut quitter");
        assertFalse(Plateau.veutQuitter(3,5),"cas où le joueur ne veut pas quitter");
        assertFalse(Plateau.veutQuitter(4, 2),"cas où le joueur ne veut pas quitter");
        assertFalse(Plateau.veutQuitter(7, 7),"cas où le joueur ne veut pas quitter");
    }

}