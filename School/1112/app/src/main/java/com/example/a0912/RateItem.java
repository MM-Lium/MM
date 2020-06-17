package com.example.a0912;

class RateItem {
    private String Currency;
    private String CashBuyRate;
    private String CashSoldRate;
    private String SpotBuyRate;
    private String SpotSoldRate;

    public RateItem(String Currency, String CashBuyRate) {
        this.Currency = Currency;
        this.CashBuyRate = CashBuyRate;
        this.CashSoldRate = CashSoldRate;
        this.SpotBuyRate = SpotBuyRate;
        this.SpotSoldRate = SpotSoldRate;
    }

    public RateItem() {
        this.Currency = "";
        this.CashBuyRate = "";
        this.CashSoldRate = "";
        this.SpotBuyRate = "";
        this.SpotSoldRate = "";
    }

    public String getSpotSoldRate() {
        return SpotSoldRate;
    }

    public void setSpotSoldRate(String spotSoldRate) {
        SpotSoldRate = spotSoldRate;
    }

    public String getSpotBuyRate() {
        return SpotBuyRate;
    }

    public void setSpotBuyRate(String spotBuyRate) {
        SpotBuyRate = spotBuyRate;
    }

    public String getCashSoldRate() {
        return CashSoldRate;
    }

    public void setCashSoldRate(String cashSoldRate) {
        CashSoldRate = cashSoldRate;
    }

    public String getCashBuyRate() {
        return CashBuyRate;
    }

    public void setCashBuyRate(String cashBuyRate) {
        CashBuyRate = cashBuyRate;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }
}