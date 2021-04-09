
const accountRoutes = (app, fs) => {

    // variables
    const dataPath = './data/accounts.json';

    // helper methods
    const readFile = (callback, returnJson = false, filePath = dataPath, encoding = 'utf8') => {
        fs.readFile(filePath, encoding, (err, data) => {
            if (err) {
                throw err;
            }

            callback(returnJson ? JSON.parse(data) : data);
        });
    };

    const writeFile = (fileData, callback, filePath = dataPath, encoding = 'utf8') => {

        fs.writeFile(filePath, fileData, encoding, (err) => {
            if (err) {
                throw err;
            }

            callback();
        });
    };

    // READ ALL
    app.get('/accounts', (req, res) => {
        fs.readFile(dataPath, 'utf8', (err, data) => {
            if (err) {
                throw err;
            }

            res.send(JSON.parse(data));
        });
    });

    // READ ONE
    app.get('/accounts/:id', (req, res) => {
        fs.readFile(dataPath, 'utf8', (err, data) => {
            if (err) {
                throw err;
            }
            const accountId = req.params["id"]
            res.send(JSON.parse(data)[accountId.toString()])
        });
    });

    // CREATE
    app.post('/accounts', (req, res) => {

        readFile(data => {
            const newaccountId = Date.now().toString();

            data[newaccountId.toString()] = req.body;

            writeFile(JSON.stringify(data, null, 2), () => {
                res.status(200).send('new account added');
            });
        },
            true);
    });


    // UPDATE
    app.put('/accounts/:id', (req, res) => {

        readFile(data => {

            const accountId = req.params["id"];
            data[accountId] = req.body;

            writeFile(JSON.stringify(data, null, 2), () => {
                res.status(200).send(`accounts id:${accountId} updated`);
            });
        },
            true);
    });


    // DELETE
    app.delete('/accounts/:id', (req, res) => {

        readFile(data => {
            const accountId = req.params["id"];
            delete data[accountId];

            writeFile(JSON.stringify(data, null, 2), () => {
                res.status(200).send(`accounts id:${accountId} removed`);
            });
        },
            true);
    });
};

module.exports = accountRoutes;
