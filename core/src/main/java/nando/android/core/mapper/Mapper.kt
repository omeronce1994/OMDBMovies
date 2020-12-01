package nando.android.core.mapper

interface Mapper<FROM, TO> {
    suspend fun map(from: FROM): TO
}