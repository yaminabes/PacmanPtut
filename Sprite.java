public abstract class Sprite {

    Model model;

    public Sprite(Model model) {
        this.model = model;
    }

    public abstract void update();
}
