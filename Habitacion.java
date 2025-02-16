public class Habitacion {
    private Baldosa[][] baldosas;
    private Gato[] gatos;
    public int ancho;
    public int largo;

    public Habitacion(int ancho, int largo, int cantidadDeGatos){
        baldosas = new Baldosa[largo][ancho];
        gatos = new Gato[cantidadDeGatos];
        this.ancho = ancho;
        this.largo = largo;

        for(int i = 0; i < largo; i++){
            for(int j = 0; j < ancho; j++){
                this.baldosas[i][j] = new Baldosa();
            }
        }

        for(int i = 0; i < cantidadDeGatos; i++){
            this.gatos[i] = posicionInicialGato();
        }

        imprimirHabitacion();
    }

    public void pasarTiempo() {
        limpiarPantalla();
        for (Gato gato : gatos) {
            gato.ensuciar(this);
            cambiarPosicionGato(gato);
        }
        imprimirHabitacion();
    }

    private void imprimirHabitacion() {
        printBorder("top");
        for(int i = 0; i < largo; i++){
            System.out.print('║');
            for(int j = 0; j < ancho; j++){
                if(comprobarSiHayGato(j, i)) {
                    System.out.print(">G<");
                }
                else {
                    System.out.print(parseSuciedad(baldosas[i][j].estadoDeSuciedad()));
                }
               
            }
            System.out.print("║\n");
        }
        printBorder("bottom");

    }

    private boolean comprobarSiHayGato(int posicionX, int posicionY) {
        for (Gato gato : gatos) {
            if(posicionX == gato.posicionX && posicionY == gato.posicionY) return true;
        }
        return false;
    }

    private String parseSuciedad(int nivel) {
             if (nivel == 0) return "   ";
        else if (nivel == 1) return " . ";
        else if (nivel == 2) return " * ";
        else if (nivel == 3) return " o ";
        else if (nivel == 4) return " O ";
        else return "eee";
    }

    private void printBorder(String border){
        String lCorner;
        String rCorner;
        
        if (border == "top"){
            lCorner = "╔";
            rCorner = "╗\n";
        }
        else if (border == "bottom") {
            lCorner = "╚";
            rCorner = "╝\n";
        } 
        else {
            lCorner = "╠";
            rCorner = "╣\n";
        }

        System.out.print(lCorner);
        for(int i = 0; i < ancho; i++) System.out.print("═══");
        System.out.print(rCorner);
    }

    private void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void ensuciarBaldosa(int posicionX, int posicionY){
        baldosas[posicionY][posicionX].ensuciarBaldosa();
    }

    public void cambiarPosicionGato(Gato gato) {
        double aleatorio = Math.random();
        if (aleatorio < .25 && gato.posicionX + 1 < this.ancho) gato.moverse('L');
        else if (aleatorio < .5 && gato.posicionX > 0) gato.moverse('R');
        else if (aleatorio < .75 && gato.posicionY + 1 < this.largo) gato.moverse('D');
        else if (aleatorio < 1 && gato.posicionY > 0) gato.moverse('U');
    }

    private Gato posicionInicialGato() {
        int posicionX = (int) Math.round(Math.random() * this.ancho);
        int posicionY = (int) Math.round(Math.random() * this.largo);
        return new Gato(posicionX, posicionY);
    }

}