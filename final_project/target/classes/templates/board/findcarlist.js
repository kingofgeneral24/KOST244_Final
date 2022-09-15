// 검색
function getSearchList(){
	$.ajax({
		type: 'GET',
		url : "/getSearchList",
		data : $("form[name=search-form]").serialize(),
		success : function(result){
			//테이블 초기화
			$('#boardtable > tbody').empty();
			if(result.length>=1){
				result.forEach(function(item){
					str='<tr>'
					str += "<td>"+item.boardNo+"</td>";
					str+="<td>"+item.boardTitle+"</td>";
					str+="<td><a href = '/board/detail?idx=" + item.boardNo + "'>" + item.boardTitle + "</a></td>";
					str+="<td>"+item.boardStartpoint+"</td>";
					str+="<td>"+item.boardEndpoint+"</td>";
					str+="<td>"+item.boardStarttime+"</td>";
					str+="<td>"+item.memberId+"</td>";
					str+="</tr>"
					$('#boardtable').append(str);
        		})				 
			}
		}
	})
}