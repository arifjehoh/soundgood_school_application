const express = require ('express');
const route = express.Router();

route.get('/', (req, res) => {
    res.send ('Hello from Rental root route.');
});

route.get('/instruments', (req, res) => {
    res.send ('List of instruments');
});

module.exports = route;
