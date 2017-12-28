function init(element){
    var frontColor = "#ff7f00", backGround = "#ffddaa";
    var r = element.clientHeight;
    element.setAttribute("width", r);
    element.setAttribute("height", r);
    var animation_loop;
    var ctx = element.getElementsByTagName("canvas")[0].getContext('2d');
    //var sp = element.getElementsByTagName("span")[0];
    var target = Math.round(element.getElementsByTagName("canvas")[0].getAttribute('data-to')), current = 1, i = 0, Pi = Math.PI;
    function drawArc(_color, _arc1, _arc2){
      ctx.beginPath();
      ctx.lineWidth = 4;
      ctx.arc(r*0.5,r*0.5,r*0.5-ctx.lineWidth/2,_arc1*Pi,_arc2*Pi,false);
      ctx.strokeStyle = _color;
      ctx.stroke();
      ctx.closePath();
    }
    function process(){
      ctx.clearRect(0, 0, r, r);
      if (current == 100) drawArc(frontColor, 0 , 2);
      else {
        drawArc(frontColor, -0.5, current*0.02-0.5);
        drawArc(backGround, current*0.02-0.5, 1.5);
      }
      //sp.innerHTML = current;
      //$('#percent').html(current);
      if (current == target) return clearInterval(animation_loop);
      else current++;
    }

    drawArc(backGround, 0, 2);
    if (typeof animation_loop != undefined) clearInterval(animation_loop);
    if (target) animation_loop = setInterval(process, 30);
  }
  function processLoad(){
    $('.am-list .circle').each(function(){
        init(this);
      });
 }
