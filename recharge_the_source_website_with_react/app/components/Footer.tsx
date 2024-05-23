import styles from "../../styles/Layout.module.css";
import React from "react";

function Footer() {
    return (
        <footer className={styles.footer}>
            <div className={styles.footerContent}>
                Datenschutz
            </div>
            <div className={styles.footerContent}>
                Impressum
            </div>
            <div className={styles.footerContent}>
                Kontakt
            </div>
        </footer>
    )
}

export default Footer;
