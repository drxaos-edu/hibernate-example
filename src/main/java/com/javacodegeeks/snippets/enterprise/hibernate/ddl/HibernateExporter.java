package com.javacodegeeks.snippets.enterprise.hibernate.ddl;

import com.javacodegeeks.snippets.enterprise.hibernate.model.Author;
import com.javacodegeeks.snippets.enterprise.hibernate.model.Book;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;

import java.io.*;

public class HibernateExporter {

    private String dialect;

    private boolean generateCreateQueries = true;
    private boolean generateDropQueries = false;

    private Configuration hibernateConfiguration;

    public HibernateExporter(String dialect) {
        this.dialect = dialect;

        hibernateConfiguration = createHibernateConfig();
    }

    public void export(OutputStream out, boolean generateCreateQueries, boolean generateDropQueries) {

        Dialect hibDialect = Dialect.getDialect(hibernateConfiguration.getProperties());
        try (PrintWriter writer = new PrintWriter(out)) {

            if (generateCreateQueries) {
                String[] createSQL = hibernateConfiguration.generateSchemaCreationScript(hibDialect);
                write(writer, createSQL, FormatStyle.DDL.getFormatter());
            }
            if (generateDropQueries) {
                String[] dropSQL = hibernateConfiguration.generateDropSchemaScript(hibDialect);
                write(writer, dropSQL, FormatStyle.DDL.getFormatter());
            }
        }
    }

    public void export(File exportFile) throws FileNotFoundException {

        export(new FileOutputStream(exportFile), generateCreateQueries, generateDropQueries);
    }

    public void exportToConsole() {

        export(System.out, generateCreateQueries, generateDropQueries);
    }

    private void write(PrintWriter writer, String[] lines, Formatter formatter) {

        for (String string : lines)
            writer.println(formatter.format(string) + ";");
    }

    private Configuration createHibernateConfig() {

        hibernateConfiguration = new Configuration();

        hibernateConfiguration.addAnnotatedClass(Author.class);
        hibernateConfiguration.addAnnotatedClass(Book.class);

        hibernateConfiguration.setProperty(AvailableSettings.DIALECT, dialect);
        return hibernateConfiguration;
    }

    public boolean isGenerateDropQueries() {
        return generateDropQueries;
    }

    public void setGenerateDropQueries(boolean generateDropQueries) {
        this.generateDropQueries = generateDropQueries;
    }

    public Configuration getHibernateConfiguration() {
        return hibernateConfiguration;
    }

    public void setHibernateConfiguration(Configuration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }

    public static void main(String[] args) {
        HibernateExporter exporter = new HibernateExporter("org.hibernate.dialect.MySQL5Dialect");
        exporter.exportToConsole();
    }
}