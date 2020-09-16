import java.net.IDN;

public class Claim {
    public final String claimId;
    public final int upperLeftX;
    public final int upperLeftY;
    public final int width;
    public final int height;

    public Claim(String claimId, int upperLeftX, int upperLeftY, int width, int height) {
        this.claimId = claimId;
        this.upperLeftX = upperLeftX;
        this.upperLeftY = upperLeftY;
        this.width = width;
        this.height = height;
    }
}
