const express = require ('express');
const app = module.exports = express();

app.use('/sgs', require ('./route/rental'));

// Simple home route
app.get ('/', (req, res) => {
    res.send('Hello from root route.')
});

const PORT = process.env.PORT || 8080;
app.listen (PORT, () => {
    console.log (`Hello World from ${PORT}`);
});
