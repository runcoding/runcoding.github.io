package com.spartajet.shardingboot.core;

/**
 * The type Data base.
 *
 * @description
 * @create 2017 -02-04 下午2:41
 * @email gxz04220427 @163.com
 */
public class Database {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Url.
     */
    private String url;
    /**
     * The Username.
     */
    private String username;
    /**
     * The Password.
     */
    private String password;
    /**
     * The Drive class name.
     */
    private String driveClassName;

    /**
     * Instantiates a new Data base.
     */
    public Database() {
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String databaseString = "name: " + this.name + " url: " + this.url + " username: " + this.username + " password: " + this.password + " driverClassName: " + this.driveClassName + " ";
        return databaseString;
    }

    /**
     * Instantiates a new Data base.
     *
     * @param name           the name
     * @param url            the url
     * @param username       the username
     * @param password       the password
     * @param driveClassName the drive class name
     */
    public Database(String name, String url, String username, String password, String driveClassName) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.driveClassName = driveClassName;
    }

    /**
     * Gets drive class name.
     *
     * @return the drive class name
     */
    public String getDriveClassName() {
        return driveClassName;
    }

    /**
     * Sets drive class name.
     *
     * @param driveClassName the drive class name
     *
     * @return the drive class name
     */
    public Database setDriveClassName(String driveClassName) {
        this.driveClassName = driveClassName;
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
    public Database setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     *
     * @return the url
     */
    public Database setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     *
     * @return the username
     */
    public Database setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     *
     * @return the password
     */
    public Database setPassword(String password) {
        this.password = password;
        return this;
    }
}
