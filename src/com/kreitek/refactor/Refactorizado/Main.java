package com.kreitek.refactor.Refactorizado;

import com.kreitek.refactor.Refactorizado.controllers.CIFController;
import com.kreitek.refactor.Refactorizado.controllers.DNIController;
import com.kreitek.refactor.Refactorizado.controllers.NIEController;

class  Main
{
    public static void main(String args[])
    {
        System.out.println("=====================");
        System.out.println("Vamos a refactorizar!");
        System.out.println("=====================");

        DNIController dniController = new DNIController();
        dniController.pintar();

        NIEController nieController = new NIEController();
        nieController.pintar();

        CIFController cifController = new CIFController();
        cifController.pintar();

    }
}