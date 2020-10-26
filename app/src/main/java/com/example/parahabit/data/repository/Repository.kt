package com.example.parahabit.data.repository

class Repository : IRepository {

    companion object{
        private var instance: Repository? = null

        fun getInstance():Repository{
            if(instance == null){
                instance = Repository()
            }
            return instance!!
        }
    }

    private var repository: IRepository? = null;

    fun getRepository(): IRepository{
        return repository!!
    }

    fun setRepository(repository: IRepository){
        this.repository = repository
    }

    override fun getHabitRepository(): IHabitRepository {
        return repository!!.getHabitRepository()
    }

    override fun getExecutionRepository(): IExecutionRepository {
        return repository!!.getExecutionRepository()
    }
}