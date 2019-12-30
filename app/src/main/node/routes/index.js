var express = require('express');
const fs = require ('fs');
var router = express.Router();
const admin = require ('firebase-admin');
const serviceAccount = ('./serviceAccountKey.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
const db = admin.firestore();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});


/* GET users listing. */
router.get('/users', function(req, res, next) {
 db.collection('utenti').get()
 .then(collection => {
   if(collection.empty){
     console.log('vuoto');
   }
   else{
     console.log('ok');
     var utenti = [];
     collection.forEach(function(document){
        utenti.push(JSON.stringify(document.data()));
     });
     console.log(utenti);
     var file = fs.writeFile('file.txt', utenti.toString(), function (err){
       if (err) throw err;
       else{
        console.log("File creato");
        res.download('D:/Documenti/GitHub/ANDROID-IS/app/src/main/node/file.txt'); 
       }
     });
   }
 })
  .catch(err =>{
    console.log('Errore', err);
    process.exit();
  }) 
});

router.get('/boh/:id',function(req,res,next){
  res.send('boh' + req.params.id);
});

router.post('/sub', function(req,res,next){
  var id = 5;
  res.redirect('/boh/' + ' ' + id);
});





module.exports = router;
