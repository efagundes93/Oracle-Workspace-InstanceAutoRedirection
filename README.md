# Oracle-Workspace-InstanceAutoRedirection

Em determinado momento, quando trabalhava na sustentação de sistemas, começou a ser recorrente a suspensão de instâncias SOA. Diante da impossibilidade de acesso direto a API da Engine da Oracle SOA Suite, para demandar a recuperação massiva das instâncias, havia necessidade de acessar a ferramenta Oracle Business Workspace e através dele redirecionar cada uma das instâncias suspensas para uma nova atividade ou simplesmente para a mesma atividade onde foi suspensa. Tal processo começou a se tornar massivo, quando diariamente entravam cerca de 40 instâncias para serem recuperadas, era uma sequencia cansativa de cliques :( 

A fim de driblar as dificuldades de acesso e obter uma forma mais "automatizada" de recuperação, desenvolvi uma aplicação que combina a linguagem de programação Java e a ferramenta de automtização de testes Web Selenium. A solução aqui disponibilizada não é a ideal, porém ajudou bastante no dia a dia da operação.

