package com.kreitek.refactor.Refactorizado.modelos;

import com.kreitek.refactor.Refactorizado.TIPODNI;
import com.kreitek.refactor.Refactorizado.TipoUltCaracter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNI implements TipoController{

    public TIPODNI enumTipo;    // tipo de documento
    public String numDNI;       // identificador del documento

    // construye un DNI
    public DNI(TIPODNI tipo, String numDNI) {
        this.enumTipo = tipo;           // tipo de documento
        this.numDNI = numDNI;           // asignamos el DNI
    }

    // valida el  documento según su tipo
    // si es ok devuelve 1
    // si es nok devuelve 0
    // si pasa algo devuelve 99
    public int validarDNI() {
        switch (this.enumTipo) {
            case DNI:
                return codigoDNI();

            case CIF:
                return codigoCIF();

            case NIE:
                return codigoNIE();

            default:
                return -99; // se ha producido un error
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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

    private int codigoCIF() {
        if (this.numDNI != null) {
            final String cifUP = this.numDNI.toUpperCase();

            // si el primer caracter no es uno de los válidos entonces ya fallamos
            if ("ABCDEFGHJKLMNPQRSUVW".indexOf(cifUP.charAt(0)) == -1) {
                return 0;
            }

            // si no cumple el patrón de CIF fallamos
            final Pattern mask = Pattern
                    .compile("[ABCDEFGHJKLMNPQRSUVW][0-9]{7}[A-Z[0-9]]");
            final Matcher matcher = mask.matcher(cifUP);
            if (!matcher.matches()) {
                return 0;
            }

            final char primerCar = cifUP.charAt(0);
            final char ultimoCar = cifUP.charAt(cifUP.length() - 1);


            TipoUltCaracter tipUltCar;

            // si empiezo por P,Q, S, K o W la última letra tiene que ser una LETRA
            if (primerCar == 'P' || primerCar == 'Q' || primerCar == 'S' || primerCar == 'K' || primerCar == 'W') {
                tipUltCar = TipoUltCaracter.LETRA;
                if (!(ultimoCar >= 'A' && ultimoCar <= 'Z')) {
                    return 0;
                }
            // si empiezo por A, B, E o H la última letra tiene que ser un número
            } else if (primerCar == 'A' || primerCar == 'B' || primerCar == 'E'
                    || primerCar == 'H') {
                tipUltCar = TipoUltCaracter.NUMERO;
                if (!(ultimoCar >= '0' && ultimoCar <= '9')) {
                    return 0;
                }
            // en otro caso la última letra puede ser cualquier cosa
            } else {
                tipUltCar = TipoUltCaracter.AMBOS;
            }


            final String digitos = cifUP.substring(1, cifUP.length() - 1);

            // sumo los pares
            int sumaPares = 0;
            for (int i = 1; i <= digitos.length() - 1; i = i + 2) {
                sumaPares += Integer.parseInt(digitos.substring(i, i + 1));
            }

            // sumo los impares
            int sumaImpares = 0;
            for (int i = 0; i <= digitos.length() - 1; i = i + 2) {
                int cal = Integer.parseInt(digitos.substring(i, i + 1)) * 2;
                if (Integer.toString(cal).length() > 1) {
                    cal = Integer.parseInt(Integer.toString(cal).substring(0, 1))
                            + Integer.parseInt(Integer.toString(cal).substring(1, 2));
                }
                sumaImpares += cal;
            }

            // los sumo todos
            final int total = sumaPares + sumaImpares;

            // calculo el número de control
            int numControl = 10 - (total % 10);

            int pos = numControl == 10? 0:numControl;
            final char carControl = "JABCDEFGHI".charAt(pos);

            // con el número de control calculado validamos
            if (tipUltCar == TipoUltCaracter.NUMERO) {

                final int ultCar = Integer.parseInt(Character
                        .toString(ultimoCar));
                if (pos != ultCar) {

                    return 0;
                }

            } else if (tipUltCar == TipoUltCaracter.LETRA) {
                if (carControl != ultimoCar) {
                    return 0;
                }

            } else {
                // find all occurrences forward
                int ultCar;

                ultCar = "JABCDEFGHI".indexOf(ultimoCar);

                if (ultCar < 0){
                    ultCar = Integer.parseInt(Character.toString(ultimoCar));
                    if (pos != ultCar) {
                        return 0;
                    }
                }
                if ((pos != ultCar) && (carControl != ultimoCar)) {
                    return 0;
                }
            }
            return 1;
        }
        return 0;
    }

    private int codigoDNI() {
        // posibles letras en un DNI
        String dniChars="TRWAGMYFPDXBNJZSQVHLCKE";
        // los primeros 8 caracteres son números
        String intPartDNI = this.numDNI.trim().replaceAll(" ", "").substring(0, 8);
        // el último es un dígito de control
        char ltrDNI = this.numDNI.charAt(8);
        // calculamos el módulo de 23 de la parte numérica que debería ser el caracter en esa
        // posición en la lista de dniChar --> my code Rocks!!!
        int valNumDni = Integer.parseInt(intPartDNI) % 23;

        // comprobamos que tutto esté bien
        if (this.numDNI.length()!= 9 || !isNumeric(intPartDNI) || dniChars.charAt(valNumDni)!= ltrDNI) {
            return 0;
        } else {
            return 1;
        }
    }
}
