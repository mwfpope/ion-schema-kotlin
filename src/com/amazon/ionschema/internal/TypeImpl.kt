/*
 * Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazon.ionschema.internal

import com.amazon.ion.*
import com.amazon.ion.system.IonSystemBuilder
import com.amazon.ionschema.Schema
import com.amazon.ionschema.internal.constraint.ConstraintBase
import com.amazon.ionschema.Violations
import com.amazon.ionschema.internal.util.markReadOnly

/**
 * Implementation of [Type] backed by a collection of zero or more [Constraint]s.
 *
 * If no "type" constraint is found, the default "type: any" is inserted by this class
 * (unless addDefaultTypeConstraint is `false`).
 */
internal class TypeImpl(
        private val ionStruct: IonStruct,
        private val schema: Schema,
        addDefaultTypeConstraint: Boolean = true
    ) : TypeInternal, ConstraintBase(ionStruct) {

    private companion object {
        private val ION = IonSystemBuilder.standard().build()
        private val ANY = ION.newSymbol("any")
    }

    override val isl = ionStruct.markReadOnly()

    internal val constraints: List<Constraint>

    init {
        var foundTypeConstraint = false
        constraints = ionStruct.asSequence()
                .filter { it.fieldName == null || (schema.getSchemaSystem() as IonSchemaSystemImpl).isConstraint(it.fieldName) }
                .onEach { if (it.fieldName.equals("type")) { foundTypeConstraint = true } }
                .map { (schema.getSchemaSystem() as IonSchemaSystemImpl).constraintFor(it, schema) }
                .toMutableList()

        if (!foundTypeConstraint && addDefaultTypeConstraint) {
            // default type is 'any':
            constraints.add(TypeReference.create(ANY, schema)())
        }
    }

    override val name = (ionStruct.get("name") as? IonSymbol)?.stringValue() ?: ionStruct.toString()

    override val schemaId: String? = (schema as? SchemaImpl)?.schemaId

    override fun getBaseType(): TypeBuiltin {
        val type = ionStruct["type"]
        if (type != null && type is IonSymbol) {
            val parentType = schema.getType(type.stringValue())
            if (parentType != null) {
                return (parentType as TypeInternal).getBaseType()
            }
        }
        return schema.getType("any")!! as TypeBuiltin
    }

    override fun isValidForBaseType(value: IonValue) = getBaseType().isValidForBaseType(value)

    override fun validate(value: IonValue, issues: Violations) {
        constraints.forEach {
            it.validate(value, issues)
        }
    }
}
