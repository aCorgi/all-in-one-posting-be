package com.corgi.allinonepostingbe.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class Custom4xxException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(msg: String) : Custom4xxException(msg)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(msg: String) : Custom4xxException(msg)
