# UOL Test


## Trello

Link utilizado para Trello: https://trello.com/b/J7pJCJeF/uol-test


## Squash e --no-ff

Não utilizei squash e adicionei --no-ff nos merges para que fosse possível visualizar melhor todos os commits em suas respectivas branchs.


## Glide vs Picasso

Glide armazena em cache a imagem já em tamanho reduzido, deixando o cache mais leve e a exibição da imagem mais rápida.
PIcasso armazena em cache a imagem 'full', tendo que redimensionar todas as vezes que for exibida. Pode ser melhor utilizado quando uma mesma imagem deva ser utilizada em diferentes locais com diferentes dimensões.


## Schedulers.io vs Schedulers.newThread

Schedulers.io utiliza um pool de threads ao invés de criar uma thread a cada utilização, como é no Schedulers.newThread.


## FragmentPageAdapter vs FragmentStatePageAdapter

FragmentPageAdapter mantém o Fragment na memória, porém é mais rápido pois não precisa recriar quando necessário.
FragmentStatePageAdapter destrói o Fragment para liberar memória, porém leva mais tempo e recurso para recriar.

A opção pelo FragmentPageAdapter foi porque o ViewPager já carrega 3 páginas por padrão (se não utilizar o método "setOffscreenPageLimit"), já carregando a nossa única tab "utilizável" ao iniciar, deixando o app mais rápido.
