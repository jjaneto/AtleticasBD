package main;

import gui.FramePrincipal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;
import sun.net.www.protocol.http.logging.HttpLogFormatter;
import utils.BancoControle;
import utils.Membro;

/**
 *
 * @author jjaneto
 */
public class IniciaPrograma {

    public static FileOutputStream fw_out;
    public static FileOutputStream fw_err;

    public static PrintStream ps_out;
    public static PrintStream ps_err;

    public static void main(String args[]) throws UnsupportedEncodingException, IOException{
        LocalDate now = LocalDate.now();
        fw_out = new FileOutputStream(now.toString() + "_out", true);
        ps_out = new PrintStream(fw_out);

        fw_err = new FileOutputStream(now.toString() + "_err", true);
        ps_err = new PrintStream(fw_err);

//        System.setOut(ps_out);
//        System.setErr(ps_err);

        System.out.println("-----[INICIO DO PROGRAMA " + LocalDateTime.now() + "]-----");
        BancoControle bdc = new BancoControle("postgres", "root", true);
        new FramePrincipal();

    }

}
