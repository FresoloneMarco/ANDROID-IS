var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});


/* GET users listing. */
router.get('/users', function(req, res, next) {
  res.send('respond with a resource');
});


router.get('/boh/:id',function(req,res,next){
  res.send('boh' + req.params.id);
});

router.post('/sub', function(req,res,next){
  var id = 5;
  res.redirect('/boh/' + ' ' + id);
});



module.exports = router;
