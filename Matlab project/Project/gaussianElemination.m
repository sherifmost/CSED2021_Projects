% algorithm for gaussian elemination returns augmented coeffecient matrix 
% and the variables matrix after solution and also the determinant. 
% parameters are the number of variables and a matrix containing the left
% hand side and a matrix containing the right hand side results of the system.
function [M,X,delta] = gaussianElemination(num,eq,res)
% assume three equations for trying the algorithm.
X = zeros(1,num);
M = zeros(num,num);
for i = 1 : num
    M(i,1 : num) = getcoefficients(char(eq(i)),num);
end
M = [M res];
delta = 1;
sign = 1;
% forward elemination phase and calculating the determinant.
for i = 1 : num-1
%pivoting part (partial pivoting to eleminate the threat of division by zero and decrease the round off errors).
[maxi MErow]=max(abs(M(i:num,i)));
MErow = MErow+i-1;
if(MErow ~=i)% a change occured.
    sign = -1*sign;
end
temp = M(MErow,1:num+1);
M(MErow,1:num+1) = M(i,1:num+1);
M(i,1:num+1) = temp;  
  delta  =delta * M(i,i);  
    for j = i+1 : num
        multiplier = M(j,i)/M(i,i);
         for k = i : num+1
             M(j,k) = M(j,k) - multiplier*M(i,k);
         end
     M(j,i) = 0;
    end
end
delta = delta * M(num,num)*sign;
% backward substitution phase.
for i = num:-1:1
    sum = 0;
    for j = i+1 : num
        sum = sum + M(i,j)*X(j);
    end
    X(i) = (M(i,num+1)-sum)/M(i,i);
end

