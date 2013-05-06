package GUI;

/*
 *  JLocaleChooser.java  - A bean for choosing locale
 * 
 * 
 */

//package com.toedter.components;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * JLocaleChooser is a bean for choosing locales.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 85 $
 * @version $LastChangedDate: 2006-04-28 13:50:52 +0200 (Fr, 28 Apr 2006) $
 */
public class JLocaleClass extends JComboBox implements ItemListener {
  private static final long serialVersionUID = 8152430789764877431L;
  protected JComponent component;

  /**
   * Default JLocale constructor.
   */
  public JLocaleClass() {
      this(null);
  }

    /**
     * Returns "JLocale".
     *
     * @return the name value
     */
    public String getName() {
        return "JLocale";
    }

  /**
   * Default JLocaleChooser constructor.
   */
  public JLocaleClass(JComponent component) {
    super();
    this.component = component;
    addItemListener(this);
    locales = Calendar.getAvailableLocales();
    localeCount = locales.length;

    for (int i = 0; i < localeCount; i++) {
      if (locales[i].getCountry().length() > 0) {
        addItem(locales[i].getDisplayName());
      }
    }

    setLocale(Locale.getDefault());
  }

  /**
   * The ItemListener for the locales.
   */
  public void itemStateChanged(ItemEvent iEvt) {
    String item = (String) iEvt.getItem();
    int i;

    for (i = 0; i < localeCount; i++) {
      if (locales[i].getDisplayName().equals(item))
        break;
    }
    setLocale(locales[i], false);
  }

  /**
   * Sets the locale.
   * 
   * @see #getLocale
   */
  private void setLocale(Locale l, boolean select) {
    Locale oldLocale = locale;
    locale = l;
    int n = 0;

    if (select) {
      for (int i = 0; i < localeCount; i++) {
        if (locales[i].getCountry().length() > 0) {
          if (locales[i].equals(locale))
            setSelectedIndex(n);
          n += 1;
        }
      }
    }

    firePropertyChange("locale", oldLocale, locale);
    if(component != null) {
        component.setLocale(l);
    }
  }

  /**
   * Sets the locale. This is a bound property.
   * 
   * @see #getLocale
   */
  public void setLocale(Locale l) {
    setLocale(l, true);
  }

  /**
   * Returns the locale.
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * Creates a JFrame with a JLocaleChooser inside and can be used for
   * testing.
   */
  static public void main(String[] s) {
    JFrame frame = new JFrame("Locale");
    frame.getContentPane().add(new JLocaleClass());
    frame.pack();
    frame.setVisible(true);
  }

  private Locale[] locales;
  private Locale locale;
  private int localeCount;
}