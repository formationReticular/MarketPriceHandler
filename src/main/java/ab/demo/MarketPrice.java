package ab.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class MarketPrice {
    private int id;
    private Instrument instrument;
    private BigDecimal bid;
    private BigDecimal ask;
    private LocalDateTime timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketPrice that = (MarketPrice) o;
        return id == that.id && instrument == that.instrument && bid.equals(that.bid) && ask.equals(that.ask) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instrument, bid, ask, timestamp);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", instrument=" + instrument +
                ", bid=" + bid +
                ", ask=" + ask +
                ", timestamp=" + timestamp +
                '}';
    }
}
