// import other routes
const accountRoutes = require('./accounts');

const appRouter = (app, fs) => {

    // default route
    app.get('/', (req, res) => {
        res.send('welcome to the ohmono api-server');
    });

    // // other routes
    accountRoutes(app, fs);

};

module.exports = appRouter;