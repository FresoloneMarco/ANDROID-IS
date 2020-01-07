var express = require('express');
const fs = require ('fs');
var router = express.Router();
const admin = require ('firebase-admin');
const PDFDocument = require('pdfkit');
const serviceAccount = ('./serviceAccountKey.json');


admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});


const storage = admin.storage();
const db = admin.firestore();

router.use(express.json());


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

//crea PDF 
router.post('/createPDF', function(req, res, next) {
  const doc = new PDFDocument;
  var bean = req.body;
  var filename = req.body.user_key + '.pdf';
  doc.pipe(fs.createWriteStream(filename));
  console.log('In server...');
  doc.text(JSON.stringify(bean));
  console.log(JSON.stringify(bean));
  doc.end();
  storage.bucket("gs://porting-android-is.appspot.com").upload('D:/Documenti/GitHub/ANDROID-IS/app/src/main/node/'+filename,
  function(err, file) {
    if (!err) {
      console.log('File caricato');
      res.send('200');
    }
    else{
      res.send('500');
      console.log(err);
    }
  });
});

//crea Excel richieste approvate
router.post('/createApprovedExcel', function(req, res, next){
  db.collection('request').where('status', '==', 'approvata').get()
  .then(snapshot => {
    if(snapshot.empty){
      console.log('Nessun risultato');
      res.send('404');
    }
    var filename = 'Accettate.xls';
    var writeStream = fs.createWriteStream(filename);
      writeStream.write('EMAIL \t NOME \t COGNOME \n');
      snapshot.forEach(doc =>{
        console.log(doc.data());
        writeStream.write(doc.get('user_key').toString());
        writeStream.write('\t');
        writeStream.write(doc.get('user_name').toString());
        writeStream.write('\t');
        writeStream.write(doc.get('user_surname').toString());
        writeStream.write('\n'); 
      })
      writeStream.close();
      storage.bucket("gs://porting-android-is.appspot.com").upload('D:/Documenti/GitHub/ANDROID-IS/app/src/main/node/'+filename,
      function(err, file) {
        if (!err) {
          console.log('File caricato');
          res.send('200');
        }
        else{
          res.send('500');
          console.log(err);
        }
      });
  })
  .catch(err =>{
    console.error('Error getting document', err);
    res.send('500');
  })
});

//crea Excel richieste rifiutate
router.post('/createRefusedExcel', function(req, res, next){
  db.collection('request').where('status', '==', 'rifiutata').get()
  .then(snapshot => {
    if(snapshot.empty){
      console.log('Nessun risultato');
      res.send('404');
    }
      var filename = 'Rifiutate.xls';
      var writeStream = fs.createWriteStream(filename);
      writeStream.write('EMAIL \t NOME \t COGNOME \n');
      snapshot.forEach(doc =>{
        console.log(doc.data());
        writeStream.write(doc.get('user_key').toString());
        writeStream.write('\t');
        writeStream.write(doc.get('user_name').toString());
        writeStream.write('\t');
        writeStream.write(doc.get('user_surname').toString());
        writeStream.write('\n'); 
      })
      writeStream.close();
      storage.bucket("gs://porting-android-is.appspot.com").upload('D:/Documenti/GitHub/ANDROID-IS/app/src/main/node/'+filename,
      function(err, file) {
        if (!err) {
          console.log('File caricato');
          res.send('200');
        }
        else{
          res.send('500');
          console.log(err);
        }
      });
  })
  .catch(err =>{
    console.error('Error getting document', err);
    res.send('500');
  })
});


module.exports = router;
