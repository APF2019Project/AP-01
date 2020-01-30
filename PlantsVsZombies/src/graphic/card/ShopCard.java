package graphic.card;

import account.Account;
import account.ShopPage;
import creature.being.BeingDna;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Program;

public class ShopCard extends SimpleCard {

    public final BeingDna dna;
    private int state;
    private ShopPage shopPage;


    public ShopCard(BeingDna dna, double size, int state, ShopPage shopPage) {
        super(dna, 0, 0, size, "");
        setColor(Color.TRANSPARENT);
        this.tName.setFill(Color.WHITESMOKE);
        this.description.setFont(Font.font(Program.screenX / 120));
        this.dna = dna;
        this.state = state;
        this.shopPage = shopPage;
        setState(state);
        this.setOnMouseClicked(e -> mouseClick());
    }

    public int getState() {
        return state;
    }

    // 0 means sold out
    // 1 means not enough money
    // 2 means buy able
    public void setState(int newState) {
        state = newState;
        if (state == 0) {
            setDescription("SOLD OUT!", Color.YELLOW);

        } else if (state == 1) {
            setDescription(dna.getShopPrice() + "$", Color.RED);
        } else {
            setDescription(dna.getShopPrice() + "$", Color.LIGHTGREEN);
        }
    }

    private void mouseClick() {
        if (state == 2) {
            Account.getCurrentAccount().getStore().buyCard(dna);
            setState(0);
            shopPage.update();
        }
    }

    public void update() {
        int money = Account.getCurrentAccount().getStore().money;
        if (state == 1) {
            if (money >= dna.getShopPrice())
                setState(2);
        } else if (state == 2) {
            if (money < dna.getShopPrice())
                setState(1);
        }
    }

}