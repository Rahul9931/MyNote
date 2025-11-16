const express = require('express');
const app = express();
const quotes = require('./quotes.json');
const userRouter = require('./routes/userRoutes');
const mongoose = require('mongoose');
const noteRouter = require('./routes/noteRoutes');
const dotenv = require('dotenv');
const cors = require('cors');

dotenv.config();

app.use(express.json());

app.use(cors());

app.use("/user", userRouter)

app.use("/note", noteRouter);

// app.get("/", (req, res)=> {
//     res.send("Welcome to the Quotes API. Use /quotes to get all quotes or /quotes/random to get a random quote.");

// })

// app.get ("/quotes", (req, res) => {
//     res.status(200).json(quotes)
// })

// app.get("/random", (req, res) => {
//     let index = Math.floor(Math.random() * quotes.length)
//     let quote = quotes[index]
//     res.status(200).json(quote)
// })

const PORT = process.env.PORT || 3000;

mongoose.connect(process.env.MONGO_URL)
.then(() => {
    app.listen(PORT, () => {
        console.log(`Server started on ${PORT} and connected with MongoDB`);
    });
})
.catch((e) => {
    console.log("MongoDB connection error", e);
});
