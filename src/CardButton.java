import javax.swing.*;

public class CardButton extends JButton
{
    private ImageIcon cardImage;
    private ImageIcon cardback;

    CardButton(ImageIcon cardImage)
    {
        this.cardImage = cardImage;
    }

    ImageIcon getCardImage() {
        return cardImage;
    }

    void setCardback(ImageIcon cardback) {
        this.cardback = cardback;
    }

    ImageIcon getCardback() {
        return cardback;
    }
}
