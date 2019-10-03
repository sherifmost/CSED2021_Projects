% algorithm for the gauss-jordan elemination method.
% takes a matrix with the equations, a matrix with the results and the
% number of variables.
% returns a matrix with the solution for the system and a flag 
% indicating the existence of this solution 1 if exists 0 if doesnt exist.
% can be used to get the inverse of the matrix by entering the identity
% matrix augmented with the coefficient matrix but would require more time
% complexity so it is not implemented as only the solution is required.
function [X,flag] = gaussJordan(num,eq,res)
X = zeros(num,1);
A = zeros(num,num);
for i = 1: num
     A(i,1 : num) = getcoefficients(char(eq(i)),num);
end
A = [A res];
flag = 1;
% elemination phase and calculating the determinant.
for i = 1 : num
%pivoting part (partial pivoting to eleminate the threat of division by zero and decrease the round off errors).
[maxi MErow]=max(abs(A(i:num,i)));
MErow = MErow+i-1;    
temp = A(MErow,1:num+1);
A(MErow,1:num+1) = A(i,1:num+1);
A(i,1:num+1) = temp;
    % scale the row
    divisor = A(i,i);
    A(i,i:num+1) = A(i,i:num+1)/divisor;
    for j = 1 : num
        if(j~=i)
            multiplier = A(j,i)/A(i,i);
         for k = i : num+1
             A(j,k) = A(j,k) - multiplier*A(i,k);
         end
        end
  
    end
    flag  = flag * A(i,i);
end
flag = flag * A(num,num);
% get rid of any round off errors.
for i = 1:num
    A(i,i+1:num) = 0;
end
% in case of the presence of a solution we return it else nans are
% returned.
if(flag == 1)
    X = A(1:num,num+1);
else
    X(1:num) = nan;
if(isnan(flag))
    flag = 0;
end
end