% algorithm for LU decomposition algorithm takes a matrix for the equations
% a matrix for the results and the number of variables, returns a matrix
% with the values of each variable, the determinant and a compact matrix containing the lower
% and upper matrices.
function [LU,X,delta] = LUDecomposition(num,eq,res)
X = zeros(1,num);
LU = zeros(num,num);
for i = 1: num
     LU(i,1 : num) = getcoefficients(char(eq(i)),num);
end
delta = 1;
sign = 1;
% decomposition using doolittle method reusing the space in LU as a compact
% matrix containing both L and U matrices using gaussian elemination.
for i = 1 : num-1
%pivoting part (partial pivoting to eleminate the threat of division by zero and decrease the round off errors).
[maxi MErow]=max(abs(LU(i:num,i)));
MErow = MErow+i-1;
if(MErow ~=i)% a change occured.
    sign = -1*sign;
end
temp = LU(MErow,1:num);
LU(MErow,1:num) = LU(i,1:num);
LU(i,1:num) = temp;
temp = res(MErow,1);
res(MErow,1) = res(i,1);
res(i,1) = temp;
  delta  =delta * LU(i,i);  
    for j = i+1 : num
        multiplier = LU(j,i)/LU(i,i);
         for k = i : num
             LU(j,k) = LU(j,k) - multiplier*LU(i,k);
         end
     LU(j,i) = multiplier;
    end
end
delta = delta * LU(num,num) *sign;
% forward substitution phase Lb=res
b = zeros(1,num); % an intermediate matrix
for i = 1: num
    sum = res(i);
    for j = 1:i-1
        sum = sum - LU(i,j)*b(j);
    end
    b(i) = sum;
end
% backward substitution phase,similar to gaussian elemination UX = b.
for i = num:-1:1
    sum = 0;
    for j = i+1:num
      
        sum = sum + LU(i,j)*X(j);
    end
    X(i) = (b(i)-sum)/LU(i,i);
end


