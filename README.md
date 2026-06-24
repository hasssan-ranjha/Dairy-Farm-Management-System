# 🐄 Ranjha Dairy Farm Management System

> **A custom-built desktop application designed to replace messy farm paperwork with real-time tracking, financial analytics, and automated reporting.**

## 📖 Overview
Running a dairy farm usually means dealing with disorganized notebooks, scattered receipts, and manual math to figure out profits. I built this application to solve that exact problem for our family business, **Ranjha Dairy & Cattle Farm**. 

This system acts as a digital ledger. It tracks the entire lifecycle of our cattle from the day they are bought to the day they are sold. It instantly calculates our financial health and allows us to export all our data into clean Excel spreadsheets with just one click. 

---

## ✨ Key Features (What it does)

* **🐄 Complete Livestock Tracking:** Keep records of every cow on the farm. The system tracks them through three main stages:
  * **Active:** Currently on the farm.
  * **Sold:** History of buyers and sale prices.
  * **Deceased:** Historical health/loss records.
* **💰 Automated Financial Dashboard:** No more manual calculators. The app instantly shows:
  * Total Investment (Money spent buying the herd)
  * Total Revenue (Money made from sales)
  * Net Profit & Loss 
* **📊 One-Click Excel Export:** Need to send a report to an accountant or business partner? Click a button, and the app automatically generates a neatly formatted `.xlsx` Excel file of your financials.
* **🔍 Instant Search & Sorting:** Easily look up a specific cow by its Tag ID or find all cows sold to a specific buyer. 
* **🌙 Dark Mode UI:** Designed with a custom dark theme and neon-glow row highlighting so the screen is easy to read, even in the harsh lighting of a barn.

---

## 🛠️ How to Use It (A Quick Guide)

1. **Adding an Animal:** Go to the main dashboard, enter the cow's Tag ID, purchase price, and date of entry. It instantly adds them to your active herd.
2. **Selling an Animal:** Select an active cow, enter the buyer's name, contact info, and sale price. The system moves the cow to the "Sold" list and automatically calculates your profit or loss on that specific animal.
3. **Checking the Money:** Open the Financials tab to see a real-time breakdown of your total investments and revenues. 
4. **Exporting Data:** Click the `Export to Excel` button to instantly save your current records to your computer.

---

## 💻 Technical Details (For Developers)

This project was engineered from the ground up using core Object-Oriented principles. 

* **Language:** Java
* **Framework:** JavaFX (UI and Application Logic)
* **Libraries:** Apache POI (For automated Excel spreadsheet generation)
* **Data Storage:** Currently utilizes flat-file **CSV** integration for lightweight, fast data persistence.
* **Architecture:** Uses custom polymorphic interfaces (`Animal`, `Cow`, `MilkProducer`) and utilizes JavaFX `FilteredList` and `SortedList` APIs for high-performance data manipulation.

---

## 🚀 Future Roadmap

This system is currently live and operational on our farm, but development is ongoing. Upcoming features include:
* **SQL Database Migration:** Transitioning from local CSV files to a robust Relational Database (SQL) to support multi-user syncing and advanced security.
* **PDF Export Engine:** Adding native PDF report generation.
* **IoT Smart Farming Integration:** Future modules will integrate with AI-based soil and irrigation sensors to manage the cultivation of the corn feed used for the cattle.

---

### 👨‍🌾 About the Developer
Built by the Co-Manager & Tech Lead of Ranjha Dairy Farm to bridge the gap between traditional agriculture and modern software engineering.
