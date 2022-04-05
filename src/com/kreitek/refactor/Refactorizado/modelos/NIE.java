package com.kreitek.refactor.Refactorizado.modelos;

import com.kreitek.refactor.Refactorizado.TIPODNI;

public class NIE implements TipoController{
    public TIPODNI enumTipo;    // tipo de documento
    public String numDNI;       // identificador del documento

    // construye un NIE
    public NIE(TIPODNI tipo, String numDNI) {
        this.enumTipo = tipo;           // tipo de documento
        this.numDNI = numDNI;           // asignamos el DNI
    }

    public NIE() {

    }

    private int codigoNIE() {
        boolean esValido = false;
        int i = 1;
        int caracterASCII;
        char letra;
        int miNIE;
        int resto;
        char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X','B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        String nie = this.numDNI;

        if(nie.length() == 9 && Character.isLetter(nie.charAt(8))
                && nie.charAt(0) == 'X'
                || nie.charAt(0) == 'Y'
                || nie.charAt(0) == 'Z') {

            do {
                caracterASCII = nie.codePointAt(i);
                esValido = (caracterASCII > 47 && caracterASCII < 58);
                i++;
            } while(i < nie.length() - 1 && esValido);
        }

        if(esValido && nie.charAt(0) == 'X') {
            nie = "0" + nie.substring(1,9);
        } else if(esValido && nie.charAt(0) == 'Y') {
            nie = "1" + nie.substring(1,9);
        } else if(esValido && nie.charAt(0) == 'Z') {
            nie = "2" + nie.substring(1,9);
        }

        if(esValido) {
            letra = Character.toUpperCase(nie.charAt(8));
            miNIE = Integer.parseInt(nie.substring(1,8));
            resto = miNIE % 23;
            esValido = (letra == asignacionLetra[resto]);
        }

        if (esValido) {
            return 1;
        } else {
            return 0;
        }
    }
}
