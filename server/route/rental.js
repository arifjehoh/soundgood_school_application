const express = require ('express');
const repository = require ('../repository/rentalRepository');
const route = express.Router();

route.get('/', (req, res) => {
    res.send ('Hello from Rental root route.');
});

route.get('/instruments', (req, res) => {
    //res.send ('List of instruments');
    //res.send (repository.getInstruments);
    res.json(repository.getInstruments());
});

module.exports = route;
