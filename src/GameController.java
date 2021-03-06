//Controller
public class GameController extends Thread {
    private PlayerData data;
    private GameData gameData = new GameData();
    private FileHandling fileHandler = new FileHandling();

    public GameController() {
        this.data = fileHandler.loadPlayerData();
    }

    @Override
    public synchronized void run() {
        while (true) {
            incrementCurrency(data.getCurPerTick());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void incrementCurrency(int amount) {
        int finalValue = data.getCurrency() + amount;
        data.setCurrency(finalValue);
    }

    public void increaseCurPerTick(int amount) {
        int finalValue = data.getCurPerTick() + amount;
        data.setCurPerTick(finalValue);

    }

    public void increaseCurPerClick(int amount) {
        int finalValue = data.getCurPerClick() + amount;
        data.setCurPerClick(finalValue);
    }


    public boolean decrementCurrency(int amount) {
        int finalValue = data.getCurrency() - amount;
        if (finalValue >= 0) {
            data.setCurrency(finalValue);
            return true;
        } else {
            return false;
        }
    }

    public void buyAllOven() {
        int currentCurrency = data.getCurrency();
        int num = currentCurrency / gameData.getOvenCost();
        this.decrementCurrency(num * gameData.getOvenCost());
        this.increaseCurPerClick(num);
    }

    public void buyAllGranny() {
        int currentCurrency = data.getCurrency();
        int num = currentCurrency / gameData.getMumCost();
        this.decrementCurrency(num * gameData.getMumCost());
        this.increaseCurPerTick(num);
    }

    public void addOven() {
        if (this.decrementCurrency(gameData.getOvenCost())) {
            this.increaseCurPerClick(1);
        }
    }

    public void addGranny() {
        if (this.decrementCurrency(gameData.getMumCost())) {
            this.increaseCurPerTick(1);
        }
    }

    public void exit() {
        fileHandler.saveToFile(data);
        System.exit(0);
    }


    public PlayerData getPlayerData() {
        return data;
    }

    public GameData getGameData() {
        return gameData;
    }


}
