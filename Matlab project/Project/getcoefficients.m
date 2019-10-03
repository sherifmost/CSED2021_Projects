function y = getcoefficients(str,num)
len = length(str);
y = zeros(1,num);
curr = '';
coef = '';
for i = 1 : len
 curr = str(i);
 if(isletter(curr))
     if(isnan(str2double(coef)))
         coef = strcat(coef,'1');
     end
     index = double(curr) - double('a') + 1;
     y(index) = str2double(coef);
     coef = '';
 else if(curr ~='*')
         coef = strcat(coef,curr);
     end
     
 end
end
end    

