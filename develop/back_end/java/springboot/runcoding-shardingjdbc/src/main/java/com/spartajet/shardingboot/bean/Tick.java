package com.spartajet.shardingboot.bean;

import java.util.Date;

/**
 * The type Tick.
 *
 * @description
 * @create 2017 -02-07 下午9:55
 * @email gxz04220427 @163.com
 */
public class Tick {
    /**
     * The Id.
     */
    private long id;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Exchange.
     */
    private String exchange;
    /**
     * The Ask.
     */
    private int ask;
    /**
     * The Bid.
     */
    private int bid;
    /**
     * The Time.
     */
    private Date time;

    /**
     * Instantiates a new Tick.
     *
     * @param id       the id
     * @param name     the name
     * @param exchange the exchange
     * @param ask      the ask
     * @param bid      the bid
     * @param time     the time
     */
    public Tick(long id, String name, String exchange, int ask, int bid, Date time) {
        this.id = id;
        this.name = name;
        this.exchange = exchange;
        this.ask = ask;
        this.bid = bid;
        this.time = time;
    }

    /**
     * Instantiates a new Tick.
     */
    public Tick() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     *
     * @return the id
     */
    public Tick setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     *
     * @return the name
     */
    public Tick setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets exchange.
     *
     * @return the exchange
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * Sets exchange.
     *
     * @param exchange the exchange
     *
     * @return the exchange
     */
    public Tick setExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    /**
     * Gets ask.
     *
     * @return the ask
     */
    public int getAsk() {
        return ask;
    }

    /**
     * Sets ask.
     *
     * @param ask the ask
     *
     * @return the ask
     */
    public Tick setAsk(int ask) {
        this.ask = ask;
        return this;
    }

    /**
     * Gets bid.
     *
     * @return the bid
     */
    public int getBid() {
        return bid;
    }

    /**
     * Sets bid.
     *
     * @param bid the bid
     *
     * @return the bid
     */
    public Tick setBid(int bid) {
        this.bid = bid;
        return this;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     *
     * @return the time
     */
    public Tick setTime(Date time) {
        this.time = time;
        return this;
    }
}
