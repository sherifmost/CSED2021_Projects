function numb = coeffFixed(strfun)
str = strfun(~isspace(strfun));
strsize = length(str);
coeff = "";
for i = 1:strsize
    coeff = ""; 
    if str(i) == 'x' && (i+1 > strsize || str(i+1) == '+' || str(i+1) == '-')
        k = i-1;
        while k>0 && str(k)~='+' && str(k)~='-'
            if str(k)~='*'
              coeff = strcat(coeff,str(k));
            end
            k = k-1;
        end
        if k==0
            if coeff == ""
                coeff = '1';
            end
            break;
        end
        if k==1 || (k-1 > 0 && str(k-1)~='^')
           coeff =  strcat(coeff,str(k));
           break;
        end
    end
end
coeff = reverse(coeff); 
if coeff == '+' 
     coeff = '1';
elseif coeff == '-'
     coeff = '-1';   
end
numb = -1*str2double(coeff);
disp(numb);


%pattern = '([-]?(\d+)?(\.)?(\d+)?)[*]?x([-+\s]|$)';
 %toks = regexp(strfun,pattern,'tokens');
 %disp(toks{1});
 %str = toks{1};
 %findings = find(cellfun('isempty',str));
 %if ~isempty(findings) && findings(1,1)==1
 %    numb = 1;
 %else
 %    ch = char(str(1,1));
 %    if ch == '-'
 %        numb = -1;
 %    else
 %    numb = str2double(str(1,1));
 %    end
 %end
 
 %numb = numb * -1;
end