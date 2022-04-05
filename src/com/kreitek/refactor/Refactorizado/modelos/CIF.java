package com.kreitek.refactor.Refactorizado.modelos;

import com.kreitek.refactor.Refactorizado.TIPODNI;
import com.kreitek.refactor.Refactorizado.TipoUltCaracter;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CIF implements TipoController{
    public TIPODNI enumTipo;    // tipo de documento
    public String numDNI;       // identificador del documento

    // construye un CIF
    public CIF(TIPODNI tipo, String numDNI) {
        this.enumTipo = tipo;           // tipo de documento
        this.numDNI = numDNI;           // asignamos el DNI
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
}
