import React, { useState, useEffect } from "react";
import axios from "axios";
import { jsPDF } from "jspdf";
import autoTable from "jspdf-autotable";

function UserDashboard() {
  const token = localStorage.getItem("token");

  const [getInfos, setGetInfo] = useState([]);
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [type, setType] = useState("");
  const [currency, setCurrency] = useState("");
  const [date, setDate] = useState("");
  const [isRecurring, setIsRecurring] = useState(false);
  const [recurrenceType, setRecurrenceType] = useState("");
  const [account_id, setAccount_id] = useState("");
  const [category_id, setCategory_id] = useState("");
  const [user, setUser] = useState("");
  const [accounts, setAccounts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  // Filters
  const [filterAccount, setFilterAccount] = useState("");
  const [filterCategory, setFilterCategory] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  // Reports
  const [weeklyReport, setWeeklyReport] = useState(null);
  const [monthlyReport, setMonthlyReport] = useState(null);

  const fetchUser = async () => {
    const res = await axios.get("http://localhost:8080/api/transactions/getUserInfo", {
      headers: { Authorization: `Bearer ${token}` },
    });
    setUser(res.data.id);
  };

  const fetchAccounts = async () => {
    const res = await axios.get("http://localhost:8080/api/accounts/getuseraccount", {
      headers: { Authorization: `Bearer ${token}` },
    });
    setAccounts(res.data);
  };

  const fetchCategories = async () => {
    const res = await axios.get("http://localhost:8080/api/categories/getusercat", {
      headers: { Authorization: `Bearer ${token}` },
    });
    setCategories(res.data);
  };

  const fetchTransactions = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/transactions/getusertx", {
        headers: { Authorization: `Bearer ${token}` },
        params: {
          accountId: filterAccount || undefined,
          categoryId: filterCategory || undefined,
          startDate: startDate || undefined,
          endDate: endDate || undefined,
        },
      });
      setGetInfo(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchReports = async () => {
    try {
      const week = await axios.get("http://localhost:8080/api/transactions/weekly-report", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setWeeklyReport(week.data);

      const month = await axios.get("http://localhost:8080/api/transactions/monthly-report", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMonthlyReport(month.data);
    } catch (err) {
      console.error("Failed to fetch reports", err);
    }
  };

  useEffect(() => {
    fetchUser();
    fetchAccounts();
    fetchCategories();
    fetchTransactions();
    fetchReports();
  }, []);

  const handleInsert = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post("http://localhost:8080/api/transactions/create", {
        amount,
        type,
        currency,
        date,
        description,
        isRecurring,
        recurrenceType,
        account_id,
        category_id,
        user_id: user,
      }, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setSuccess("✅ Transaction created successfully");
      setError("");
      fetchTransactions();
      fetchReports();
    } catch (error) {
      setError(error.response?.data || "Transaction Failed");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/transactions/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setSuccess("✅ Deleted");
      fetchTransactions();
      fetchReports();
    } catch (err) {
      setError("❌ Failed to delete");
    }
  };

  const exportCSV = () => {
    const rows = getInfos.map(tx => [
      tx.amount, tx.type, tx.currency, tx.date, tx.description,
      tx.account_id, tx.category_id
    ]);

    const csvContent =
      "Amount,Type,Currency,Date,Description,Account ID,Category ID\n" +
      rows.map(row => row.join(",")).join("\n");

    const blob = new Blob([csvContent], { type: "text/csv" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "transactions.csv";
    a.click();
  };

  const exportPDF = () => {
    const doc = new jsPDF();
    doc.text("Transactions Report", 20, 10);
    autoTable(doc, {
      head: [["Amount", "Type", "Currency", "Date", "Description", "Account", "Category"]],
      body: getInfos.map(tx => [
        tx.amount,
        tx.type,
        tx.currency,
        tx.date,
        tx.description,
        tx.account_id,
        tx.category_id,
      ]),
    });
    doc.save("transactions.pdf");
  };

  return (
    <div>
      <h2>User Dashboard - Transactions</h2>

      {success && <p style={{ color: "green" }}>{success}</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      <h4>📊 Filters</h4>
      <div>
        <select value={filterAccount} onChange={(e) => setFilterAccount(e.target.value)}>
          <option value="">-- Filter by Account --</option>
          {accounts.map(acc => (
            <option key={acc.id} value={acc.id}>{acc.name}</option>
          ))}
        </select>

        <select value={filterCategory} onChange={(e) => setFilterCategory(e.target.value)}>
          <option value="">-- Filter by Category --</option>
          {categories.map(cat => (
            <option key={cat.id} value={cat.id}>{cat.name}</option>
          ))}
        </select>

        <input
          type="date"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
        />
        <input
          type="date"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
        />
        <button onClick={fetchTransactions}>Apply Filters</button>
      </div>

      <h4>📥 Add Transaction</h4>
<form onSubmit={handleInsert}>
  <div>
    <label>Amount:</label>
    <input
      type="number"
      value={amount}
      onChange={(e) => setAmount(e.target.value)}
      required
    />
  </div>

  <div>
    <label>Type:</label>
    <select
      value={type}
      onChange={(e) => setType(e.target.value)}
      required
    >
      <option value="">-- Select Type --</option>
      <option value="INCOME">INCOME</option>
      <option value="EXPENSE">EXPENSE</option>
    </select>
  </div>

  <div>
    <label>Currency:</label>
    <input
      type="text"
      value={currency}
      onChange={(e) => setCurrency(e.target.value)}
      required
    />
  </div>

  <div>
    <label>Date:</label>
    <input
      type="date"
      value={date}
      onChange={(e) => setDate(e.target.value)}
      required
    />
  </div>

  <div>
    <label>Description:</label>
    <input
      type="text"
      value={description}
      onChange={(e) => setDescription(e.target.value)}
    />
  </div>

  <div>
    <label>Recurring:</label>
    <select
      value={isRecurring}
      onChange={(e) => setIsRecurring(e.target.value === "true")}
    >
      <option value="false">No</option>
      <option value="true">Yes</option>
    </select>
  </div>

  {isRecurring && (
    <div>
      <label>Recurrence Type:</label>
      <select
        value={recurrenceType}
        onChange={(e) => setRecurrenceType(e.target.value)}
      >
        <option value="">-- Select Recurrence --</option>
        <option value="DAILY">Daily</option>
        <option value="WEEKLY">Weekly</option>
        <option value="MONTHLY">Monthly</option>
      </select>
    </div>
  )}

  <div>
    <label>Account:</label>
    <select
      value={account_id}
      onChange={(e) => setAccount_id(e.target.value)}
      required
    >
      <option value="">-- Select Account --</option>
      {accounts.map((acc) => (
        <option key={acc.id} value={acc.id}>
          {acc.name}
        </option>
      ))}
    </select>
  </div>

  <div>
    <label>Category:</label>
    <select
      value={category_id}
      onChange={(e) => setCategory_id(e.target.value)}
      required
    >
      <option value="">-- Select Category --</option>
      {categories.map((cat) => (
        <option key={cat.id} value={cat.id}>
          {cat.name}
        </option>
      ))}
    </select>
  </div>

  <button type="submit" disabled={loading}>
    {loading ? "Saving..." : "➕ Add Transaction"}
  </button>
</form>


      <h4>📃 Transactions</h4>
      <button onClick={exportCSV}>Export CSV</button>
      <button onClick={exportPDF}>Export PDF</button>
      <ul>
        {getInfos.length > 0 ? getInfos.map(tx => (
          <li key={tx.id}>
            {tx.amount} - {tx.type} - {tx.date} - {tx.description}
            <button onClick={() => handleDelete(tx.id)}>🗑️</button>
          </li>
        )) : <li>No transactions found</li>}
      </ul>

      <h4>📅 Weekly Report</h4>
      {weeklyReport && (
        <ul>
          <li>From: {weeklyReport.from}</li>
          <li>To: {weeklyReport.to}</li>
          <li>Total Income: {weeklyReport.totalIncome}</li>
          <li>Total Expense: {weeklyReport.totalExpense}</li>
          <li>Net Balance: {weeklyReport.netBalance}</li>
        </ul>
      )}

      <h4>📆 Monthly Report</h4>
      {monthlyReport && (
        <ul>
          <li>Month: {monthlyReport.month}</li>
          <li>Total Income: {monthlyReport.totalIncome}</li>
          <li>Total Expense: {monthlyReport.totalExpense}</li>
          <li>Net Balance: {monthlyReport.netBalance}</li>
        </ul>
      )}
    </div>
  );
}

export default UserDashboard;
