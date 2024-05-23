import styles from "../styles/Layout.module.css"
import React from "react";
import Footer from "./components/Footer";

export const metadata = {
    title: 'Recharge The Source',
    description: 'Website about recycling of smartphones',
    icons: './earth.ico',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en" >
    <body className={styles.body}>
        {children}
        <Footer></Footer>
    </body>
    </html>
  )
}
